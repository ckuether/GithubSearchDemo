package com.example.githubsearchdemo.deserializers

import com.example.githubsearchdemo.models.User
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UsersMapDeserializer : JsonDeserializer<LinkedHashMap<String, User>> {

    override fun deserialize(json: JsonElement?, typeOfT: Type, context: JsonDeserializationContext): LinkedHashMap<String, User> {
        val usersMap = linkedMapOf<String, User>()

        if(json != null){
            val jsonObj = json.asJsonObject
            if(jsonObj.has("items")) {
                val usersJson = jsonObj.get("items").asJsonArray
                val usersList: ArrayList<User> = Gson().fromJson(usersJson, object : TypeToken<ArrayList<User>>() {}.type)
                for(user in usersList){
                    usersMap[user.userName] = user
                }
            }
        }

        return usersMap
    }
}