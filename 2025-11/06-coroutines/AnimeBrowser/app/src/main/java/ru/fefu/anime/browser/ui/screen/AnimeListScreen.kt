package ru.fefu.anime.browser.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.fefu.anime.browser.model.Anime
import ru.fefu.anime.browser.model.AnimeFilter
import ru.fefu.anime.browser.ui.viewmodel.AnimeUiState
import ru.fefu.anime.browser.ui.widget.AnimeCard
import ru.fefu.anime.browser.ui.widget.FilterRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    state: AnimeUiState,
    anime: List<Anime>,
    onSearch: () -> Unit,
    onSearchChange: (String) -> Unit,
    onFilterChange: (AnimeFilter) -> Unit,
    onToggleFavourite: (Int) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Anime Browser") }) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {

            OutlinedTextField(
                value = state.query,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Anime by Title") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onSearch, enabled = state.query.isNotBlank() && !state.isLoading) {
                    Text(if (state.isLoading) "Loading..." else "Search")
                }
            }

            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Error: $it")
            }

            Spacer(modifier = Modifier.height(16.dp))

            FilterRow(filter = state.filter, onFilterChange = onFilterChange)

            Spacer(modifier = Modifier.height(12.dp))

            if (!state.isLoading && anime.isEmpty()) {
                Text("Ничего нету!")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(items = anime, key = { it.id }) { item ->
                        AnimeCard(
                            anime = item,
                            isFavourite = item.id in state.favourites,
                            onToggleFavourite = { onToggleFavourite(item.id) },
                            onClick = {  }
                        )
                    }
                }
            }
        }
    }
}