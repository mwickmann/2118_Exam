package com.example.pgr208_exam.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pgr208_exam.database.converter.ProductTypeConverter
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(ProductTypeConverter::class)
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    @SerializedName("image")
    val imageUrl: String,
    val rating: Rating
)

data class Rating(
    val rate: Double,
    val count: Int
)
