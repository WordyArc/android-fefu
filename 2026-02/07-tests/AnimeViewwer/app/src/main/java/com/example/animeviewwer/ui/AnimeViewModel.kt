package com.example.animeviewwer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeviewwer.data.AnimeRepository
import com.example.animeviewwer.model.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

data class AnimeListUiState(
    val searchQuery: String = "",
    val animeList: List<Anime> = emptyList(),
    val favouriteList: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasSearched: Boolean = false,
)


@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    var uiState by mutableStateOf(AnimeListUiState())
        private set

    private var searchJob: Job? = null

    init {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            try {
                val favourites = repository.getFavourites()
                val favouriteIds = favourites.map { it.id }.toSet()

                uiState = uiState.copy(
                    favouriteList = favourites,
                    animeList = uiState.animeList.map { anime ->
                        anime.copy(isFavourite = anime.id in favouriteIds)
                    }
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                uiState = uiState.copy(
                    errorMessage = "Не удалось загрузить избранное"
                )
            }
        }
    }

    fun onSearchQueryChange(newValue: String) {
        uiState = uiState.copy(
            searchQuery = newValue,
            errorMessage = null
        )

        searchJob?.cancel()

        val query = newValue.trim()

        if (query.isBlank()) {
            uiState = uiState.copy(
                animeList = emptyList(),
                isLoading = false,
                errorMessage = null,
                hasSearched = false
            )
            loadFavourites()
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)

            uiState = uiState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val result = repository.searchAnime(query)

                if (query != uiState.searchQuery.trim()) return@launch

                uiState = uiState.copy(
                    animeList = result,
                    isLoading = false,
                    errorMessage = null,
                    hasSearched = true
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Ошибка загрузки",
                    hasSearched = true
                )
            }
        }
    }

    fun onFavouriteClick(anime: Anime) {
        viewModelScope.launch {
            try {
                repository.toggleFavourite(anime)

                val favourites = repository.getFavourites()
                val favouriteIds = favourites.map { it.id }.toSet()

                uiState = uiState.copy(
                    favouriteList = favourites,
                    animeList = uiState.animeList.map { currentAnime ->
                        currentAnime.copy(isFavourite = currentAnime.id in favouriteIds)
                    }
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                uiState = uiState.copy(
                    errorMessage = "Не удалось сохранить избранное"
                )
            }

        }
    }
}
