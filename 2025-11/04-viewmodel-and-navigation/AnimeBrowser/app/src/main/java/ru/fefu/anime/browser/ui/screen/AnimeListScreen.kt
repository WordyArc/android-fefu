package ru.fefu.anime.shelf.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.fefu.anime.shelf.model.Anime
import ru.fefu.anime.shelf.model.AnimeFilter
import ru.fefu.anime.shelf.ui.viewmodel.AnimeUiState
import ru.fefu.anime.shelf.ui.widget.AnimeCard
import ru.fefu.anime.shelf.ui.widget.FilterRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    state: AnimeUiState,
    anime: List<Anime>,
    onSearchChange: (String) -> Unit,
    onFilterChange: (AnimeFilter) -> Unit,
    onToggleFavourite: (Int) -> Unit,
    onSelect: (Int) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Anime Browser") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {

            OutlinedTextField(
                value = state.query,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Anime by Title") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilterRow(filter = state.filter, onFilterChange = onFilterChange)

            Spacer(modifier = Modifier.height(12.dp))

            if (anime.isEmpty()) {
                Text("Ничего нету!")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(items = anime, key = { it.id }) { item ->
                        AnimeCard(
                            anime = item,
                            isFavourite = item.id in state.favourites,
                            onToggleFavourite = { onToggleFavourite(item.id) },
                            onClick = { onSelect(item.id) }
                        )
                    }
                }
            }
        }
    }
}