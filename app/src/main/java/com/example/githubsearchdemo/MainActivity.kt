package com.example.githubsearchdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.githubsearchdemo.fragments.UserSearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragStack: ArrayList<Fragment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToSearchUsersFragment()
    }

    private fun goToSearchUsersFragment(){
        val userSearchFrag = UserSearchFragment.newInstance()
        fragStack.add(userSearchFrag)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(frag_container.id, userSearchFrag).commit()
    }
}
