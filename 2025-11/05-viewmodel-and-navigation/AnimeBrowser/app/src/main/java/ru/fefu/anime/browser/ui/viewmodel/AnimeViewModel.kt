package ru.fefu.anime.shelf.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.fefu.anime.shelf.data.sampleAnimeList
import ru.fefu.anime.shelf.model.Anime
import ru.fefu.anime.shelf.model.AnimeFilter



data class AnimeUiState(
    val query: String = "",
    val filter: AnimeFilter = AnimeFilter.ALL,
    val favourites: Set<Int> = emptySet(),
)

class AnimeViewModel(private val allAnime: List<Anime> = sampleAnimeList) : ViewModel() {

    var uiState by mutableStateOf(AnimeUiState())
        private set

    fun onQueryChange(query: String) {
        uiState = uiState.copy(query = query)
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

    fun animeById(id: Int): Anime? =
        allAnime.firstOrNull { it.id == id }

    val visibleAnime: List<Anime>
        get() {
            val q = uiState.query
            val byQuery = if (q.isBlank()) allAnime
            else allAnime.filter { it.title.contains(q, ignoreCase = true) }

            return when (uiState.filter) {
                AnimeFilter.ALL -> byQuery
                AnimeFilter.FAVOURITES -> byQuery.filter { it.id in uiState.favourites }
            }
        }
}
