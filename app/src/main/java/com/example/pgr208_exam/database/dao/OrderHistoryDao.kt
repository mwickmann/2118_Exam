package com.example.pgr208_exam.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgr208_exam.database.entities.OrderHistory
import java.util.Date

// sql spørringer fraorderhistory som henter ut alle, enkelte, og legger til
@Dao
interface OrderHistoryDao {
    @Query("SELECT * FROM order_history")
    suspend fun getOrders(): List<OrderHistory>

    @Query("SELECT * FROM order_history WHERE :orderId = orderId")
    suspend fun getOrderById(orderId: Int): OrderHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderHistory)

    @Query("SELECT * FROM order_history WHERE orderId IN (:idList)")
    suspend fun getOrdersByIds(idList: List<Int>): List<OrderHistory>



}