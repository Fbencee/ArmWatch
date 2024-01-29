package com.example.myapplication.converter

import androidx.room.TypeConverter
import com.example.myapplication.entity.Cart
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartItemTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<Cart>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<Cart> {
        val type = object : TypeToken<List<Cart>>() {}.type
        return gson.fromJson(json, type)
    }
}
