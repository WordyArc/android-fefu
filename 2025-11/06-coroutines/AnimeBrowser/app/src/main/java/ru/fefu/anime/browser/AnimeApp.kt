package ru.fefu.anime.browser

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.fefu.anime.browser.ui.screen.AnimeListScreen
import ru.fefu.anime.browser.ui.viewmodel.AnimeViewModel


sealed class AnimeRoute(val route: String) {

    data object List : AnimeRoute("list")

    data object Detail : AnimeRoute("detail/{id}") {
        const val ARG_ID = "id"
        fun createRoute(id: Int): String = "detail/$id"
    }
}

@Composable
fun AnimeApp() {
    val holder: AnimeViewModel = viewModel()
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