package com.example.myapplication.converter

import androidx.room.TypeConverter
import com.example.myapplication.entity.User
import com.google.gson.Gson

class UserTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromFood(user: User): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun toFood(json: String): User {
        return gson.fromJson(json, User::class.java)
    }
}
