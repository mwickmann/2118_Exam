package com.example.pgr208_exam.database.converter

import androidx.room.TypeConverter
import com.example.pgr208_exam.database.entities.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingJson: String): Rating {
        return gson.fromJson(ratingJson, Rating::class.java)
    }
}
