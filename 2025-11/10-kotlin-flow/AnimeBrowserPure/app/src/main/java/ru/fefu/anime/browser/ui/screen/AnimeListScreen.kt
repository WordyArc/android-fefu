package ru.fefu.anime.browser.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.fefu.anime.browser.model.AnimeFilter
import ru.fefu.anime.browser.ui.viewmodel.AnimeUiState
import ru.fefu.anime.browser.ui.widget.AnimeCard
import ru.fefu.anime.browser.ui.widget.FilterRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    state: AnimeUiState,
    onSearch: () -> Unit,
    onSearchChange: (String) -> Unit,
    onFilterChange: (AnimeFilter) -> Unit,
    onToggleFavourite: (Int) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Anime Browser") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Поиск аниме") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSearch,
                enabled = state.query.isNotBlank() && !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Найти")
                }
            }

            state.errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FilterRow(
                filter = state.filter,
                onFilterChange = onFilterChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            val anime = state.visibleAnime

            when {
                state.isLoading && anime.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                !state.isLoading && anime.isEmpty() -> {
                    Text(
                        text = if (state.filter == AnimeFilter.FAVOURITES) {
                            "В избранном пока пусто"
                        } else {
                            "Ничего не найдено"
                        }
                    )
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(
                            items = anime,
                            key = { it.id }
                        ) { item ->
                            AnimeCard(
                                anime = item,
                                isFavourite = item.id in state.favouriteIds,
                                onToggleFavourite = { onToggleFavourite(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}