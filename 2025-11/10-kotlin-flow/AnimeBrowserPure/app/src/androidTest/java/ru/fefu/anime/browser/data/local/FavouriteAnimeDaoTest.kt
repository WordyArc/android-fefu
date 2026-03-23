package ru.fefu.anime.browser.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
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
    fun insert_AndGetAll_returnsItemsOrderedByAddedAtDesc() = runTest {
        // given
        val oldItem = FavouriteAnimeEntity(
            1,
            "Naruto",
            2002,
            "Action",
            220,
            100
        )

        val newItem = FavouriteAnimeEntity(
            2,
            "Bleach",
            2004,
            "Action",
            320,
            200
        )

        // when
        dao.insert(oldItem)
        dao.insert(newItem)
        val result = dao.getAll()

        // then
        assertEquals(listOf(newItem, oldItem), result)
    }

}