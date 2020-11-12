package com.example.githubsearchdemo.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchdemo.R
import com.example.githubsearchdemo.callbacks.UserSearchCallback
import com.example.githubsearchdemo.inflate
import com.example.githubsearchdemo.models.User
import com.example.githubsearchdemo.retrofit.GithubInterface
import com.example.githubsearchdemo.retrofit.Retro
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class UsersAdapter(var mCallback: UserSearchCallback) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    val LOG_TAG = UsersAdapter::class.java.simpleName

    private var users: ArrayList<User> = mCallback.getUsersList()


    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = parent.inflate(R.layout.row_user)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.user_name_tv.text = user.userName

        Picasso.get().load(user.avatarUrl).placeholder(R.drawable.ic_user_placeholder).into(holder.user_image)
        if(user.publicReposCount == -1) {
            getUserDetails(user, position)
        }else{
            holder.repos_count_tv.text = "Number of repos ${user.publicReposCount}"
        }
    }

    private fun getUserDetails(user: User, position: Int){

        val mCall = Retro.getGithubRetro().create(GithubInterface::class.java).getUser(user.userName)
        mCall.enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful && response.body() != null){
                    val fullUser: User = response.body()!!
                    mCallback.updateUser(fullUser)
                    notifyItemChanged(position)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(LOG_TAG, t.message)
            }
        })
    }

    fun updateUsers(){
        users = mCallback.getUsersList()
        notifyDataSetChanged()
    }

    inner class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val user_image = view.findViewById<ImageView>(R.id.user_image)
        val user_name_tv = view.findViewById<TextView>(R.id.user_name_tv)
        val repos_count_tv = view.findViewById<TextView>(R.id.repos_count_tv)
    }
}