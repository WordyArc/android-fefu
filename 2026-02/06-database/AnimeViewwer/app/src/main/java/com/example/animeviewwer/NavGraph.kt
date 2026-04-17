package com.example.animeviewwer

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animeviewwer.ui.AnimeViewModel
import com.example.animeviewwer.ui.screens.AnimeListScreen

@Composable
fun NavGraph() {
    val animeViewModel: AnimeViewModel = hiltViewModel()

    AnimeListScreen(
        uiState = animeViewModel.uiState,
        onSearchChange = animeViewModel::onSearchQueryChange,
        onFavoriteClick = animeViewModel::onFavouriteClick,
    )
}