package com.example.githubsearchdemo.retrofit

import com.example.githubsearchdemo.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubInterface {

    @GET("search/users")
    fun searchUsers(@Query("q") searchQuery: String): Call<LinkedHashMap<String, User>>

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Call<User>
}