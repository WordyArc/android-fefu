package com.example.animeviewwer.data

import com.example.animeviewwer.data.remote.JikanApi
import com.example.animeviewwer.data.remote.toDomainOrNull
import com.example.animeviewwer.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AnimeRepository {
    suspend fun searchAnime(query: String): List<Anime>
}

class AnimeRepositoryImpl @Inject constructor(private val api: JikanApi) : AnimeRepository {

    override suspend fun searchAnime(query: String): List<Anime> = withContext(Dispatchers.IO) {
        api.searchAnime(query = query).data.mapNotNull { dto ->
            dto.toDomainOrNull()
        }
    }
}


class FakeRepository : AnimeRepository {
    override suspend fun searchAnime(query: String): List<Anime>  =
        listOf(Anime(1, "213", 2, "123", 213))
}