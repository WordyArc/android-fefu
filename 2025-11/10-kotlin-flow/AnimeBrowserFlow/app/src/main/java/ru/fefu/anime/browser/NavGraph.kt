package ru.fefu.anime.browser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fefu.anime.browser.ui.screen.AnimeListScreen
import ru.fefu.anime.browser.ui.viewmodel.AnimeViewModel

sealed class AnimeRoute(val route: String) {
    data object List : AnimeRoute("list")
}

@Composable
fun NavGraph() {
    val viewModel: AnimeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AnimeListScreen(
        state = state,
        onSearch = viewModel::refresh,
        onSearchChange = viewModel::onQueryChange,
        onFilterChange = viewModel::onFilterChange,
        onToggleFavourite = viewModel::onToggleFavourite,
    )
}