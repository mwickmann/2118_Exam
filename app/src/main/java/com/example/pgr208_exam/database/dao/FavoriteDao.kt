package com.example.pgr208_exam.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgr208_exam.database.entities.Favorite

// sql spørringer fra favoritter som legger til, henter og fjerner
@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    suspend fun getFavorites(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun removeFavorite(favorite: Favorite)
}