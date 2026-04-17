package com.example.animeviewwer.data

import com.example.animeviewwer.data.local.AnimeDao
import com.example.animeviewwer.data.local.toDomain
import com.example.animeviewwer.data.local.toFavouriteEntity
import com.example.animeviewwer.data.remote.JikanApi
import com.example.animeviewwer.data.remote.toDomainOrNull
import com.example.animeviewwer.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val api: JikanApi,
    private val animeDao: AnimeDao
) {

    suspend fun getFavourites(): List<Anime> = withContext(Dispatchers.IO) {
        animeDao.getFavourites().map { it.toDomain() }
    }

    suspend fun toggleFavourite(anime: Anime) = withContext(Dispatchers.IO) {
        if (anime.isFavourite) {
            animeDao.deleteById(anime.id)
        } else {
            animeDao.upsert(anime.toFavouriteEntity())
        }
    }

    suspend fun searchAnime(query: String): List<Anime> = withContext(Dispatchers.IO) {
        val favouriteIds = animeDao.getFavouritesIds().toSet()

        api.searchAnime(query = query).data
            .mapNotNull { dto -> dto.toDomainOrNull() }
            .map { anime -> anime.copy(isFavourite = anime.id in favouriteIds) }
    }
}
