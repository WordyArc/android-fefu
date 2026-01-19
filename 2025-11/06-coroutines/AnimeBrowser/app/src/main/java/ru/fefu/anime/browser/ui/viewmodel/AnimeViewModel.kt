package ru.fefu.anime.browser.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.fefu.anime.browser.data.AnimeRepository
import ru.fefu.anime.browser.model.Anime
import ru.fefu.anime.browser.model.AnimeFilter



data class AnimeUiState(
    val query: String = "",
    val filter: AnimeFilter = AnimeFilter.ALL,
    val favourites: Set<Int> = emptySet(),

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class AnimeViewModel(private val repository: AnimeRepository = AnimeRepository()) : ViewModel() {

    var uiState by mutableStateOf(AnimeUiState())
        private set

    private var items by mutableStateOf(emptyList<Anime>())

    fun onQueryChange(query: String) {
        uiState = uiState.copy(query = query, errorMessage = null)
    }

    fun onFilterChange(filter: AnimeFilter) {
        uiState = uiState.copy(filter = filter)
    }

    fun onToggleFavourites(id: Int) {
        val favourites = uiState.favourites
        uiState = uiState.copy(
            favourites = if (id in favourites) favourites - id else favourites + id
        )
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
            AnimeFilter.FAVOURITES -> items.filter { it.id in uiState.favourites }
        }
}
