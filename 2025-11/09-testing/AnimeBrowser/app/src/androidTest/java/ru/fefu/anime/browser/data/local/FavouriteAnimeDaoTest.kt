package ru.fefu.anime.browser.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteAnimeDaoTest {

    private lateinit var database: AnimeDatabase
    private lateinit var dao: FavouriteAnimeDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AnimeDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.favouriteAnimeDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insert_andGetAll_returnsItemsOrderedByAddedAtDesc() = runTest {
        // Arrange
        val oldItem = FavouriteAnimeEntity(
            id = 1,
            title = "Naruto",
            year = 2002,
            genre = "Action",
            episodes = 220,
            addedAt = 100
        )

        val newItem = FavouriteAnimeEntity(
            id = 2,
            title = "Bleach",
            year = 2004,
            genre = "Action",
            episodes = 366,
            addedAt = 200
        )

        // Act
        dao.insert(oldItem)
        dao.insert(newItem)
        val result = dao.getAll()

        // Assert
        assertEquals(listOf(newItem, oldItem), result)
    }

    @Test
    fun deleteById_removesItem() = runTest {
        // Arrange
        val item = FavouriteAnimeEntity(
            id = 1,
            title = "Naruto",
            year = 2002,
            genre = "Action",
            episodes = 220,
            addedAt = 100
        )
        dao.insert(item)

        // Act
        dao.deleteById(1)
        val result = dao.getAll()

        // Assert
        assertEquals(emptyList<FavouriteAnimeEntity>(), result)
    }
}