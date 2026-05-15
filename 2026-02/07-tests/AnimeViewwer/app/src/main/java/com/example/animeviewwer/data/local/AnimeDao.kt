package com.example.animeviewwer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDao {

    @Query("SELECT * FROM favourite_anime ORDER BY title")
    suspend fun getFavourites(): List<FavouriteAnimeEntity>

    @Query("SELECT id FROM favourite_anime")
    suspend fun getFavouritesIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(anime: FavouriteAnimeEntity)

    @Query("DELETE FROM favourite_anime WHERE id = :id")
    suspend fun deleteById(id: Int)

}