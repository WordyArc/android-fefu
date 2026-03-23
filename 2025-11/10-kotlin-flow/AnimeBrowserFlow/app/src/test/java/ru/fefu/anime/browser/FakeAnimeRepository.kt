package ru.fefu.anime.browser

import ru.fefu.anime.browser.data.AnimeRepository
import ru.fefu.anime.browser.model.Anime

class FakeAnimeRepository : AnimeRepository {

    val favourites: MutableList<Anime>  = mutableListOf()
    val searchResult: List<Anime>  = emptyList()

    var failGetFavourites = false
    var failSearch = false

    override suspend fun addFavourite(anime: Anime) {
        favourites.removeAll { it.id == anime.id }
        favourites.add(0, anime)
    }

    override suspend fun removeFavourite(id: Int) {
        favourites.removeAll { it.id == id }
    }

    override suspend fun getFavourites(): List<Anime> {
        if (failGetFavourites) error("getFavourites failed")
        return favourites.toList()
    }

    override suspend fun searchAnime(query: String): List<Anime> {
        if (failSearch) error("search failed")
        return searchResult
    }
}