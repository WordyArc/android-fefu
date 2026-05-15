package com.example.animeviewwer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.animeviewwer.data.local.AnimeDao
import com.example.animeviewwer.data.local.AnimeDatabase
import com.example.animeviewwer.data.local.FavouriteAnimeEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnimeDaoTest {

    private lateinit var database: AnimeDatabase

    private lateinit var dao: AnimeDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AnimeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.animeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsertDeleteAndReadFavourites() = runTest {
        val olderTitle = FavouriteAnimeEntity(
            2,
            "Naruto",
            2002,
            "Adventure",
            220
        )

        val firstByTitle = FavouriteAnimeEntity(
            1,
            "Bleach",
            2004,
            "Action",
            400
        )

        dao.upsert(olderTitle)
        dao.upsert(firstByTitle)
        dao.deleteById(2)

        Assert.assertEquals(listOf(firstByTitle), dao.getFavourites())
        Assert.assertEquals(listOf(1), dao.getFavouritesIds())
    }

}