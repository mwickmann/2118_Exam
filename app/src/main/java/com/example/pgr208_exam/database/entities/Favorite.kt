package com.example.pgr208_exam.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite (
    @PrimaryKey
    val productId: Int
)