package com.example.animeviewwer.di

import android.content.Context
import androidx.room.Room
import com.example.animeviewwer.data.local.AnimeDao
import com.example.animeviewwer.data.local.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providAnimeDatabase(
        @ApplicationContext context: Context
    ): AnimeDatabase =
        Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            "anime.db",
        ).build()

    @Provides
    @Singleton
    fun provideAnimeDao(
        database: AnimeDatabase
    ): AnimeDao = database.animeDao()

}