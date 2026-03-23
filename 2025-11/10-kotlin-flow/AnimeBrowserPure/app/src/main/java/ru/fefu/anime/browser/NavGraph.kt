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
    val viewModel: AnimeViewModel = hiltViewModel()

    AnimeListScreen(
        state = viewModel.uiState,
        onSearch = viewModel::search,
        onSearchChange = viewModel::onQueryChange,
        onFilterChange = viewModel::onFilterChange,
        onToggleFavourite = viewModel::onToggleFavourite,
    )
}