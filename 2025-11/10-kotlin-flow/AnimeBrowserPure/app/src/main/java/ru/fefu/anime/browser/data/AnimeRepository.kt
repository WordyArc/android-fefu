package ru.fefu.anime.browser.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fefu.anime.browser.data.local.FavouriteAnimeDao
import ru.fefu.anime.browser.data.local.FavouriteAnimeEntity
import ru.fefu.anime.browser.data.local.toDomain
import ru.fefu.anime.browser.data.remote.JikanAnimeDto
import ru.fefu.anime.browser.data.remote.JikanApi
import ru.fefu.anime.browser.data.remote.JikanSearchResponse
import ru.fefu.anime.browser.data.remote.toDomainOrNull
import ru.fefu.anime.browser.model.Anime
import javax.inject.Inject


interface AnimeRepository {
    suspend fun addFavourite(anime: Anime)

    suspend fun removeFavourite(id: Int)

    suspend fun getFavourites(): List<Anime>

    suspend fun searchAnime(query: String): List<Anime>
}

class AnimeRepositoryImpl @Inject constructor(
    private val api: JikanApi,
    private val favouriteDao: FavouriteAnimeDao,
) : AnimeRepository {
    override suspend fun addFavourite(anime: Anime) = withContext(Dispatchers.IO) {
        favouriteDao.insert(FavouriteAnimeEntity(
            id = anime.id,
            title = anime.title,
            year = anime.year,
            genre = anime.genre,
            episodes = anime.episodes,
        ))
    }

    override suspend fun removeFavourite(id: Int) = withContext(Dispatchers.IO) {
        favouriteDao.deleteById(id)
    }

    override suspend fun getFavourites(): List<Anime> = withContext(Dispatchers.IO) {
        favouriteDao.getAll().map { it.toDomain() }
    }

    override suspend fun searchAnime(query: String): List<Anime> = withContext(Dispatchers.IO) {
        api.searchAnime(query = query).data.mapNotNull { it.toDomainOrNull() }
    }
}
