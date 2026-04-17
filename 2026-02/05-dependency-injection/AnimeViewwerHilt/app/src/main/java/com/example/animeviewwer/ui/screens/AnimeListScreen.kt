package com.example.animeviewwer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.animeviewwer.ui.AnimeListUiState
import com.example.animeviewwer.ui.widgets.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    uiState: AnimeListUiState,
    onSearchChange: (String) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Anime Viewwer") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search by title") },
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            uiState.errorMessage?.let {
                Text("Error: $it")
                Spacer(modifier = Modifier.height(8.dp))
            }

            when {
                uiState.isLoading -> {
                    Text("Loading...")
                }

                !uiState.hasSearched -> {
                    Text("Введите название аниме")
                }

                uiState.animeList.isEmpty() -> {
                    Text("Ничего не найдено")
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = uiState.animeList,
                            key = { it.id }
                        ) { anime ->
                            AnimeCard(
                                anime = anime,
                            )
                        }
                    }
                }
            }
        }
    }
}