package com.example.myapplication.converter

import androidx.room.TypeConverter
import com.example.myapplication.entity.Watch
import com.google.gson.Gson

class WatchTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromWatch(watch: Watch): String {
        return gson.toJson(watch)
    }

    @TypeConverter
    fun toWatch(json: String): Watch {
        return gson.fromJson(json, Watch::class.java)
    }
}
