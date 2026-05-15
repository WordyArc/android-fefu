package com.example.animeviewwer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animeviewwer.model.Anime

@Entity(tableName = "favourite_anime")
data class FavouriteAnimeEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val episodes: Int,
)

fun FavouriteAnimeEntity.toDomain(): Anime = Anime(
    id,
    title,
    year,
    genre,
    episodes,
    isFavourite = true,
)

fun Anime.toFavouriteEntity(): FavouriteAnimeEntity = FavouriteAnimeEntity(
    id,
    title,
    year,
    genre,
    episodes,
)
