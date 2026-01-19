package ru.fefu.anime.browser.model

data class Anime(
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val episodes: Int,
)