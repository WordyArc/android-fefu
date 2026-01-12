@file:OptIn(ExperimentalMaterial3Api::class)

package ru.fefu.anime.browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AnimeApp()
                }
            }
        }
    }
}

data class Anime(
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val episodes: Int,
)


enum class AnimeFilter {
    ALL,
    FAVOURITES
}

data class AnimeUiState(
    val query: String = "",
    val filter: AnimeFilter = AnimeFilter.ALL,
    val favourites: Set<Int> = emptySet(),
    val selectedId: Int? = null,
)

val sampleAnimeList = listOf(
    Anime(1, "Code Geass", 2006, "Mecha", 50),
    Anime(2, "Frieren", 2023, "Fantasy", 28),
    Anime(3, "Attack on Titan", 2013, "Action, Drama", 75),
    Anime(4, "Demon Slayer", 2019, "Action, Fantasy", 44),
    Anime(5, "Shaman King", 2001, "Adventure", 64),
    Anime(6, "Jujutsu Kaisen", 2020, "Action, Supernatural", 24),
    Anime(7, "Berserk", 1997, "Dark Fantasy", 25),
)


class AnimeStateHolder(private val allAnime: List<Anime> = sampleAnimeList) : ViewModel() {

    var uiState by mutableStateOf(AnimeUiState())

    fun onQueryChange(query: String) {
        uiState = uiState.copy(query = query)
    }

    fun onFilterChange(filter: AnimeFilter) {
        uiState = uiState.copy(filter = filter)
    }

    fun onToggleFavourites(id: Int) {
        val favourites = uiState.favourites
        uiState = uiState.copy(
            favourites = if (id in favourites) favourites - id else favourites + id
        )
    }

    fun onSelect(id: Int) {
        uiState = uiState.copy(selectedId = id)
    }

    fun selectedAnime(): Anime? =
        allAnime.firstOrNull { it.id == uiState.selectedId }

    fun visibleAnime(): List<Anime> {
        val query = uiState.query
        val filter = uiState.filter

        val byQuery = if (query.isBlank()) {
            allAnime
        } else {
            allAnime.filter { it.title.contains(query, ignoreCase = true) }
        }

        return when (filter) {
            AnimeFilter.ALL -> byQuery
            AnimeFilter.FAVOURITES -> byQuery.filter { it.id in uiState.favourites }
        }
    }

    fun onBack() {
        uiState = uiState.copy(selectedId = null)
    }

}


@Composable
fun AnimeApp() {
    val holder: AnimeStateHolder = viewModel()
    val state = holder.uiState

    if (state.selectedId == null) {
        AnimeListScreen(
            state = state,
            anime = holder.visibleAnime(),
            onSearchChange = holder::onQueryChange,
            onFilterChange = holder::onFilterChange,
            onToggleFavourite = holder::onToggleFavourites,
            onSelect = holder::onSelect
        )
    } else {
        val anime = holder.selectedAnime()
        if (anime == null) {
            holder.onBack()
        } else {
            AnimeDetailScreen(
                anime = anime,
                isFavourite = anime.id in state.favourites,
                onBack = { holder.onBack() },
                onToggleFavourite = { holder.onToggleFavourites(anime.id) }
            )
        }
    }


}


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
        topBar = { TopAppBar(title = { Text("") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            OutlinedTextField(
                value = state.query,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Anime by Title") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilterRow(filter = state.filter, onFilterChange = onFilterChange)

            if (anime.isEmpty()) {
                Text("Ничего нету!")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(items = anime, key = { it.id }) { item ->
                        AnimeRow(
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

@Composable
fun FilterRow(filter: AnimeFilter, onFilterChange: (AnimeFilter) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(
            onClick = { onFilterChange(AnimeFilter.ALL) },
            enabled = filter != AnimeFilter.ALL
        ) { Text(if (filter == AnimeFilter.ALL) "- ALl" else "All" ) }
        TextButton(
            onClick = { onFilterChange(AnimeFilter.FAVOURITES) },
            enabled = filter != AnimeFilter.FAVOURITES
        ) { Text(if (filter == AnimeFilter.FAVOURITES) "- FAVOURITES" else "FAVOURITES" ) }
    }
}

@Composable
fun AnimeRow(anime: Anime, isFavourite: Boolean, onToggleFavourite: () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = anime.title,
                fontWeight = FontWeight.Bold
            )
            Text("${anime.year} - ${anime.genre}")
            Text("Episodes: ${anime.episodes}")
            Text(text = if (isFavourite) "+" else "-", modifier = Modifier.clickable(onClick = onToggleFavourite))
        }
    }
}

@Composable
fun AnimeDetailScreen(
    anime: Anime,
    isFavourite: Boolean,
    onBack: () -> Unit,
    onToggleFavourite: () -> Unit,
) {
    Scaffold(topBar = { TopAppBar(title = { Text("Detail Screen") }) }) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {
            TextButton(onClick = onBack) { Text("Назад") }

            Spacer(Modifier.height(12.dp))
            Text(
                text = anime.title,
                fontWeight = FontWeight.Bold
            )
            Text("${anime.year} - ${anime.genre}")
            Text("Episodes: ${anime.episodes}")
            Spacer(Modifier.height(12.dp))

            Text(
                text = if (isFavourite) "+" else "-",
                modifier = Modifier.clickable(onClick = onToggleFavourite)
            )
        }
    }
}