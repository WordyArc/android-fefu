package com.example.animeviewwer.data

import com.example.animeviewwer.NetworkModule
import com.example.animeviewwer.data.remote.JikanApi
import com.example.animeviewwer.data.remote.toDomainOrNull
import com.example.animeviewwer.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepository(private val api: JikanApi = NetworkModule.api) {

    suspend fun searchAnime(query: String): List<Anime> = withContext(Dispatchers.IO) {
        api.searchAnime(query = query).data.mapNotNull { dto ->
            dto.toDomainOrNull()
        }
    }
}