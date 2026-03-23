package ru.fefu.anime.browser.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavouriteAnimeEntity)

    @Query("DELETE FROM favourite_anime WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM favourite_anime ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<FavouriteAnimeEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_anime WHERE id = :id)")
    suspend fun isFavourite(id: Int): Boolean

    @Query("SELECT * FROM favourite_anime ORDER BY addedAt DESC")
    suspend fun getAll(): List<FavouriteAnimeEntity>
}
