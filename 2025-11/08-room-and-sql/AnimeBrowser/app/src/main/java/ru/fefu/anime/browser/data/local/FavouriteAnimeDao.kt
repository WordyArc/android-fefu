package ru.fefu.anime.browser.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavouriteAnimeEntity)

    @Query("DELETE FROM favourite_anime WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM favourite_anime ORDER BY addedAt DESC")
    suspend fun getAll(): List<FavouriteAnimeEntity>
}
