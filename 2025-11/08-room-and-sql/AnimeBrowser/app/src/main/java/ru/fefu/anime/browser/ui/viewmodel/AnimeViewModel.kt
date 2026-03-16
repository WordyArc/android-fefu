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
    val favourites: Set<Int> = emptySet(),

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class AnimeViewModel @Inject constructor(private val repository: AnimeRepository) : ViewModel() {

    var uiState by mutableStateOf(AnimeUiState())
        private set

    private var items by mutableStateOf(emptyList<Anime>())

    private var favouritesItems by mutableStateOf(emptyList<Anime>())

    init {
        loadFavourites()
    }

    private fun loadFavourites() {
        viewModelScope.launch {
            try {
                val favs = repository.getFavourites()
                favouritesItems = favs
                uiState = uiState.copy(favourites = favs.map { it.id }.toSet())
            } catch (ex: Exception) {
                uiState = uiState.copy(
                    errorMessage = "Не удалось загрузить избранное"
                )
            }
        }
    }

    fun onQueryChange(query: String) {
        uiState = uiState.copy(query = query, errorMessage = null)
    }

    fun onFilterChange(filter: AnimeFilter) {
        uiState = uiState.copy(filter = filter)
    }

    fun onToggleFavourites(id: Int) {
        viewModelScope.launch {
            val currentItems = favouritesItems
            val currentIds = uiState.favourites

            if (id in currentIds) {
                repository.removeFavourite(id)
                favouritesItems = currentItems.filterNot { it.id == id }
                uiState = uiState.copy(favourites = currentIds - id)
            } else {
                val anime = items.firstOrNull() { it.id == id }
                if (anime == null) {
                    uiState = uiState.copy(errorMessage = "Не удалось добавить в избранное")
                    return@launch
                }
                repository.addFavourite(anime)
                favouritesItems = listOf(anime) + currentItems
                uiState = uiState.copy(favourites = currentIds + id)
            }
        }
    }

    private var searchJob: Job? = null

    fun search() {
        val query = uiState.query.trim()
        if (query.isBlank()) {
            uiState = uiState.copy(errorMessage = "Введите запрос")
            return
        }

        searchJob?.cancel()
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val result = repository.searchAnime(query)
                items = result
                uiState = uiState.copy(isLoading = false)
            } catch (ex: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Что-то пошло не так"
                )
            }
        }
    }

    val visibleAnime: List<Anime>
        get() = when (uiState.filter) {
            AnimeFilter.ALL -> items
            AnimeFilter.FAVOURITES -> favouritesItems
        }
}
