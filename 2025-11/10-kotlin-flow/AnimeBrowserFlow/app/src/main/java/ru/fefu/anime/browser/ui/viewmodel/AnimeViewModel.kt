package ru.fefu.anime.browser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
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
    val visibleAnime: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasSearched: Boolean = false,
) {
    val favouriteIds: Set<Int>
        get() = favourites.map { it.id }.toSet()

    val canRefresh: Boolean
        get() = query.isNotBlank() && !isLoading
}

private data class SearchState(
    val items: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasSearched: Boolean = false,
)

private sealed interface SearchEvent {
    data object Reset : SearchEvent
    data object Loading : SearchEvent
    data class Success(val items: List<Anime>) : SearchEvent
    data class Error(val message: String) : SearchEvent
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")
    private val filterFlow = MutableStateFlow(AnimeFilter.ALL)

    private val refreshRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    private val favouritesFlow: StateFlow<List<Anime>> =
        repository.observeFavourites()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    private val searchQueryFlow =
        queryFlow
            .map { it.trim() }
            .debounce(400)
            .distinctUntilChanged()

    private val searchState: StateFlow<SearchState> =
        combine(
            searchQueryFlow,
            refreshRequests.onStart { emit(Unit) }
        ) { query, _ ->
            query
        }
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(SearchEvent.Reset)
                } else {
                    flow {
                        emit(SearchEvent.Loading)
                        emit(SearchEvent.Success(repository.searchAnime(query)))
                    }.catch {
                        emit(SearchEvent.Error("Что-то пошло не так"))
                    }
                }
            }
            .scan(SearchState()) { previous, event ->
                when (event) {
                    SearchEvent.Reset -> SearchState()

                    SearchEvent.Loading -> previous.copy(
                        isLoading = true,
                        errorMessage = null
                    )

                    is SearchEvent.Success -> SearchState(
                        items = event.items,
                        isLoading = false,
                        errorMessage = null,
                        hasSearched = true
                    )

                    is SearchEvent.Error -> previous.copy(
                        isLoading = false,
                        errorMessage = event.message,
                        hasSearched = true
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchState()
            )

    val uiState: StateFlow<AnimeUiState> =
        combine(
            queryFlow,
            filterFlow,
            favouritesFlow,
            searchState,
        ) { rawQuery, filter, favourites, search ->
            val effectiveQuery = rawQuery.trim()

            val visibleAnime = when (filter) {
                AnimeFilter.ALL -> search.items

                AnimeFilter.FAVOURITES -> {
                    if (effectiveQuery.isBlank()) {
                        favourites
                    } else {
                        favourites.filter {
                            it.title.contains(effectiveQuery, ignoreCase = true)
                        }
                    }
                }
            }

            AnimeUiState(
                query = rawQuery,
                filter = filter,
                searchResults = search.items,
                favourites = favourites,
                visibleAnime = visibleAnime,
                isLoading = search.isLoading,
                errorMessage = search.errorMessage,
                hasSearched = search.hasSearched
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AnimeUiState()
        )

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }

    fun onFilterChange(filter: AnimeFilter) {
        filterFlow.value = filter
    }

    fun refresh() {
        refreshRequests.tryEmit(Unit)
    }

    fun onToggleFavourite(anime: Anime) {
        viewModelScope.launch {
            if (repository.isFavourite(anime.id)) {
                repository.removeFavourite(anime.id)
            } else {
                repository.addFavourite(anime)
            }
        }
    }
}