package ru.fefu.anime.browser.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.fefu.anime.browser.data.local.AnimeDatabase
import ru.fefu.anime.browser.data.local.FavouriteAnimeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase =
        Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            "anime_browser.db"
        ).build()

    @Provides
    fun providesFavouriteDao(db: AnimeDatabase): FavouriteAnimeDao =
        db.favouriteAnimeDao()
}
