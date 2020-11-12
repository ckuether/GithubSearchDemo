package com.example.githubsearchdemo.retrofit

import com.example.githubsearchdemo.deserializers.UserDeserializer
import com.example.githubsearchdemo.deserializers.UsersMapDeserializer
import com.example.githubsearchdemo.models.User
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Retro {

    private val GITHUB_URL = "https://api.github.com/"

    private var githubRetro: Retrofit? = null

    fun getGithubRetro(): Retrofit {
        if(githubRetro == null){
            val gsonBuilder = GsonBuilder()

            gsonBuilder.registerTypeAdapter(object : TypeToken<LinkedHashMap<String, User>>(){}.type, UsersMapDeserializer())
            gsonBuilder.registerTypeAdapter(User::class.java, UserDeserializer())

            githubRetro = initBuilder(gsonBuilder)
        }
        return githubRetro!!
    }

    private fun initBuilder(gsonBuilder: GsonBuilder) : Retrofit{
        val client = OkHttpClient.Builder()
        val builder = Retrofit.Builder()
        builder.baseUrl(GITHUB_URL)
        builder.client(client.build())

        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        return builder.build()
    }
}