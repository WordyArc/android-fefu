package ru.fefu.anime.browser.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.fefu.anime.browser.data.AnimeRepository
import ru.fefu.anime.browser.model.Anime
import ru.fefu.anime.browser.model.AnimeFilter
import javax.inject.Inject


data class AnimeUiState(
    val query: String = "",
    val filter: AnimeFilter = AnimeFilter.ALL,
    val searchResults: List<Anime> = emptyList(),
    val favourites: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {
    val favouriteIds: Set<Int>
        get() = favourites.map { it.id }.toSet()

    val visibleAnime: List<Anime>
        get() = when (filter) {
            AnimeFilter.ALL -> searchResults
            AnimeFilter.FAVOURITES -> favourites
        }
}

@HiltViewModel
class AnimeViewModel @Inject constructor(private val repository: AnimeRepository) : ViewModel() {

    var uiState by mutableStateOf(AnimeUiState())
        private set

    private var searchJob: Job? = null

    init {
        loadFavourites()
    }

    private fun updateState(transform: (AnimeUiState) -> AnimeUiState) {
        uiState = transform(uiState)
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            runCatching {
                repository.getFavourites()
            }.onSuccess { favourites ->
                updateState { current ->
                    current.copy(favourites = favourites)
                }
            }.onFailure {
                updateState { current ->
                    current.copy(errorMessage = "Не удалось загрузить избранное")
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        updateState { current ->
            current.copy(
                query = query,
                errorMessage = null,
            )
        }
    }

    fun onFilterChange(filter: AnimeFilter) {
        updateState { current ->
            current.copy(filter = filter)
        }
    }

    fun onToggleFavourite(id: Int) {
        val currentState = uiState
        val isFavourite = id in currentState.favouriteIds

        viewModelScope.launch {
            runCatching {
                if (isFavourite) {
                    repository.removeFavourite(id)
                } else {
                    val anime = (currentState.searchResults + currentState.favourites)
                        .firstOrNull { it.id == id }
                        ?: throw IllegalStateException("Anime with id=$id not found")

                    repository.addFavourite(anime)
                }
            }.onSuccess {
                loadFavourites()
            }.onFailure {
                updateState { current ->
                    current.copy(
                        errorMessage = if (isFavourite) {
                            "Не удалось удалить из избранного"
                        } else {
                            "Не удалось добавить в избранное"
                        }
                    )
                }
            }
        }
    }

    fun search() {
        val query = uiState.query.trim()

        if (query.isBlank()) {
            updateState { current ->
                current.copy(errorMessage = "Введите запрос")
            }
            return
        }

        searchJob?.cancel()

        updateState { current ->
            current.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        searchJob = viewModelScope.launch {
            runCatching {
                repository.searchAnime(query)
            }.onSuccess { result ->
                updateState { current ->
                    current.copy(
                        searchResults = result,
                        isLoading = false,
                    )
                }
            }.onFailure {
                updateState { current ->
                    current.copy(
                        isLoading = false,
                        errorMessage = "Что-то пошло не так"
                    )
                }
            }
        }
    }
}
