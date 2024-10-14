package com.example.pgr208_exam.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgr208_exam.database.entities.Cart

// sql spørringer fra cart som legger til, endrer og sletter fra cart
@Dao
interface CartDao {
        @Query("SELECT * FROM Cart")
        suspend fun getCarts(): List<Cart>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCart(cart: Cart)

        @Delete
        suspend fun removeCart(cart: Cart)
}