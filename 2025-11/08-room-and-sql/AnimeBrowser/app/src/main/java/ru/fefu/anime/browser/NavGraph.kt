package ru.fefu.anime.browser

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.fefu.anime.browser.ui.screen.AnimeListScreen
import ru.fefu.anime.browser.ui.viewmodel.AnimeViewModel


sealed class AnimeRoute(val route: String) {

    data object List : AnimeRoute("list")
}

@Composable
fun NavGraph() {
    val holder: AnimeViewModel = hiltViewModel()
    val state = holder.uiState

    AnimeListScreen(
        state = state,
        anime = holder.visibleAnime,
        onSearch = holder::search,
        onSearchChange = holder::onQueryChange,
        onFilterChange = holder::onFilterChange,
        onToggleFavourite = holder::onToggleFavourites,
    )
}