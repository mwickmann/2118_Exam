package com.example.pgr208_exam.database.converter

import androidx.room.TypeConverter
import com.example.pgr208_exam.database.entities.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductListTypeConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun fromJson(value: String): List<Product> {
        val listType = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun toJson(list: List<Product>): String {
        return gson.toJson(list)
    }
}