package com.example.pgr208_exam.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "order_history")
data class OrderHistory(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val date: Date,
    val time: Double,
    val items: List<Product>
)

