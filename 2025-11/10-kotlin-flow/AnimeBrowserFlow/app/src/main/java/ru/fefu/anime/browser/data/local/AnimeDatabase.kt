package ru.fefu.anime.browser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteAnimeEntity::class],
    version = 1,
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun favouriteAnimeDao(): FavouriteAnimeDao
}
