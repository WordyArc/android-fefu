package ru.fefu.anime.browser.data.remote

import com.google.gson.annotations.SerializedName
import ru.fefu.anime.browser.model.Anime

data class JikanSearchResponse(
    val data: List<JikanAnimeDto> = emptyList()
)

data class JikanAnimeDto(
    @SerializedName("mal_id")
    val id: Int,
    val title: String? = null,
    val year: Int? = null,
    val episodes: Int? = null,
    val genres: List<JikanNamedDto>? = null,
)

data class JikanNamedDto(
    val name: String? = null
)

fun JikanAnimeDto.toDomainOrNull(): Anime? {
    val safeTitle: String = title ?: return null
    val genresStr = genres
        ?.mapNotNull { it.name }
        ?.joinToString { ", " }
        .orEmpty()
        .ifBlank { "Unknown" }


    return Anime(
        id = id,
        title = safeTitle,
        year = year ?: 0,
        genre = genresStr,
        episodes = episodes ?: 0
    )
}
