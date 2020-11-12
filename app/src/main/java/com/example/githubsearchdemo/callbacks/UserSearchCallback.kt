package com.example.githubsearchdemo.callbacks

import com.example.githubsearchdemo.models.User

interface UserSearchCallback {

    fun getUsersList(): ArrayList<User>
    fun updateUser(user: User)
}