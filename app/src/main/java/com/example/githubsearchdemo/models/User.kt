package com.example.githubsearchdemo.models

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("login")
    lateinit var userName: String
    @SerializedName("avatar_url")
    lateinit var avatarUrl: String
    @SerializedName("followers")
    var followers: Int = 0
    @SerializedName("following")
    var following: Int = 0
    @SerializedName("public_repos")
    var publicReposCount: Int = -1

}