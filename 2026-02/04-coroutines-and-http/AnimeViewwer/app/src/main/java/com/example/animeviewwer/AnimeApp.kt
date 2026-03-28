package com.example.animeviewwer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.animeviewwer.ui.AnimeViewModel
import com.example.animeviewwer.ui.screens.AnimeListScreen

@Composable
fun AnimeApp() {
    val animeViewModel: AnimeViewModel = viewModel()

    AnimeListScreen(
        uiState = animeViewModel.uiState,
        onSearchChange = animeViewModel::onSearchQueryChange,
    )
}