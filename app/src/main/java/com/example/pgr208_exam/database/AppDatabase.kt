package com.example.pgr208_exam.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pgr208_exam.database.entities.Cart
import com.example.pgr208_exam.database.entities.Favorite
import com.example.pgr208_exam.database.entities.OrderHistory
import com.example.pgr208_exam.database.entities.Product
import com.example.pgr208_exam.database.dao.CartDao
import com.example.pgr208_exam.database.converter.DateConverter
import com.example.pgr208_exam.database.dao.FavoriteDao
import com.example.pgr208_exam.database.dao.OrderHistoryDao
import com.example.pgr208_exam.database.dao.ProductDao
import com.example.pgr208_exam.database.converter.ProductListTypeConverter
import com.example.pgr208_exam.database.converter.ProductTypeConverter

@TypeConverters(value = [DateConverter::class, ProductTypeConverter::class, ProductListTypeConverter::class])
@Database(
    entities = [Product::class, Cart::class, Favorite::class, OrderHistory::class],
    version = 9,
    exportSchema = false // (nb, må alltid være med!)
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun orderHistoryDao(): OrderHistoryDao
}