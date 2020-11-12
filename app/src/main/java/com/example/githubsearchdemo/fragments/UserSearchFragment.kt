package com.example.githubsearchdemo.fragments

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearchdemo.R
import com.example.githubsearchdemo.adapters.UsersAdapter
import com.example.githubsearchdemo.callbacks.UserSearchCallback
import com.example.githubsearchdemo.inflate
import com.example.githubsearchdemo.models.User
import com.example.githubsearchdemo.retrofit.GithubInterface
import com.example.githubsearchdemo.retrofit.Retro
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_search.*
import kotlinx.android.synthetic.main.toolbar_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSearchFragment : Fragment(), UserSearchCallback {
    private val LOG_TAG = UserSearchFragment::class.java.simpleName

    private lateinit var mHandler: Handler

    private lateinit var usersAdapter: UsersAdapter
    private var usersMap: LinkedHashMap<String, User> = linkedMapOf()

    companion object {
        fun newInstance(): UserSearchFragment{
            return UserSearchFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mHandler = Handler()
        usersAdapter = UsersAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container?.inflate(R.layout.fragment_user_search)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_search_toolbar.toolbar.search_et.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchQuery = s.toString().trim()
                val isEmpty = TextUtils.isEmpty(searchQuery)

                mHandler.removeCallbacksAndMessages(null)

                if(!isEmpty){
                    mHandler.postDelayed({ searchUsers(searchQuery)}, 750)
                }else{
                    clearSearch()
                }
                updateSearchEt()
            }
        })

        user_search_toolbar.remove_search_btn.setOnClickListener {
            clearSearch()
        }

        users_rv.layoutManager = LinearLayoutManager(context)
        users_rv.adapter = usersAdapter
    }

    override fun getUsersList(): ArrayList<User> {
        val users: ArrayList<User> = arrayListOf()
        for(userKey in usersMap.keys){
            val user = usersMap[userKey]!!
            users.add(user)
        }
        return users
    }

    override fun updateUser(user: User) {
        usersMap[user.userName] = user
    }

    private fun clearSearch(){
        user_search_toolbar.toolbar.search_et.text = null
        usersAdapter.updateUsers()
        updateSearchEt()
    }

    private fun updateSearchEt(){
        val searchQuery: String = user_search_toolbar.toolbar.search_et.text.toString()
        val isEmpty = TextUtils.isEmpty(searchQuery)

        if(!isEmpty){
            mHandler.postDelayed({ searchUsers(searchQuery)}, 750)
            user_search_toolbar.remove_search_btn.visibility = View.VISIBLE
        }else{
            user_search_toolbar.remove_search_btn.visibility = View.GONE
        }
    }

    private fun searchUsers(searchQuery: String){
        val mCall = Retro.getGithubRetro().create(GithubInterface::class.java).searchUsers(searchQuery)
        mCall.enqueue(object: Callback<LinkedHashMap<String, User>>{
            override fun onResponse(call: Call<LinkedHashMap<String, User>>, response: Response<LinkedHashMap<String, User>>) {
                if(response.isSuccessful && response.body() != null){
                    usersMap = response.body()!!
                    usersAdapter.updateUsers()
                }
            }

            override fun onFailure(call: Call<LinkedHashMap<String, User>>, t: Throwable) {
                Log.e(LOG_TAG, t.message)
            }
        })
    }
}