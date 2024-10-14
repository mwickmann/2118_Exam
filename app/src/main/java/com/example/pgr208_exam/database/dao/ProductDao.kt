package com.example.pgr208_exam.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgr208_exam.database.entities.Product

// sql spørringer fra som hentr ut alle (fra api) etter id og etter kategori
@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    suspend fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM Product WHERE id = :productId")
    suspend fun getProductById(productId: Int): Product?

    //Lagt til
    @Query("SELECT * FROM Product WHERE id IN (:idList)")
    suspend fun getProductsByIds(idList: List<Int?>): List<Product>

    @Query("SELECT * FROM Product WHERE category = :category")
    suspend fun getProductsByCategory(category: String): List<Product>


}
