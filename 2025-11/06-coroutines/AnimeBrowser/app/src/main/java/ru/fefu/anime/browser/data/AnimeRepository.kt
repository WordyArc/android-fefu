package ru.fefu.anime.browser.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fefu.anime.browser.NetworkModule
import ru.fefu.anime.browser.data.remote.JikanApi
import ru.fefu.anime.browser.data.remote.toDomainOrNull
import ru.fefu.anime.browser.model.Anime

class AnimeRepository(private val api: JikanApi = NetworkModule.api) {
    suspend fun searchAnime(query: String): List<Anime> = withContext(Dispatchers.IO) {
        api.searchAnime(query = query).data.mapNotNull { it.toDomainOrNull() }
    }
}
