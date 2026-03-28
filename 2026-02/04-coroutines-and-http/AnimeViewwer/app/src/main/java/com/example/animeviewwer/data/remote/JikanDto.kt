package com.example.animeviewwer.data.remote

import com.example.animeviewwer.model.Anime
import com.google.gson.annotations.SerializedName

data class JikanSearchResponse(
    val data: List<JikanAnimeDto> = emptyList()
)

data class JikanAnimeDto(
    @SerializedName("mal_id")
    val id: Int,
    val title: String? = null,
    val year: Int? = null,
    val episodes: Int? = null,
    val genres: List<JikanNamedDto>? = null
)

data class JikanNamedDto(
    val name: String? = null
)

fun JikanAnimeDto.toDomainOrNull(): Anime? {
    val sageTitle = title ?: return null

    val genresString = genres
        ?.mapNotNull { it.name }
        ?.joinToString(", ")
        .orEmpty()
        .ifBlank { "Unknown" }

    return Anime(
        id,
        sageTitle,
        year ?: 0,
        genresString,
        episodes ?: 0,
    )
}