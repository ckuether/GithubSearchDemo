package com.example.githubsearchdemo.deserializers

import com.example.githubsearchdemo.models.User
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class UserDeserializer : JsonDeserializer<User> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): User {
        val user: User = Gson().fromJson(json!!.asJsonObject, User::class.java)
        return user
    }
}