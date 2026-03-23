package ru.fefu.anime.browser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.fefu.anime.browser.model.Anime


@Entity(tableName = "favourite_anime")
data class FavouriteAnimeEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val episodes: Int,
    val addedAt: Long = System.currentTimeMillis()
)

fun FavouriteAnimeEntity.toDomain(): Anime =
    Anime(id, title, year, genre, episodes)