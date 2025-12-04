@file:OptIn(ExperimentalMaterial3Api::class)

package ru.fefu.anime.browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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

val sampleAnimeList = listOf(
    Anime(1, "Code Geass", 2006, "Mexa", 50),
    Anime(2, "Frieren", 2003, "Fantasy", 50),
    Anime(2, "Attack on Titan", 2013, "Action, Drama", 75),
    Anime(5, "Demon Slayer", 2019, "Action, Fantasy", 44),
    Anime(5, "Shaman King", 2003, "Mexa", 50),
    Anime(6, "Jujutsu Kaisen", 2020, "Action, Supernatural", 24),
    Anime(7, "Berserk", 2003, "Mexa", 50),
)


@Composable
fun AnimeApp() {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredList = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            sampleAnimeList
        } else {
            sampleAnimeList.filter { anime ->
                anime.title.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    AnimeListScreen(filteredList, searchQuery, { searchQuery = it })
}


@Composable
fun AnimeListScreen(
    anime: List<Anime>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search Anime by Title") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (anime.isEmpty()) {
                Text("Ничего нету!")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(items = anime, key = { it.id }) { item ->
                        AnimeRow(
                            anime = item
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AnimeRow(anime: Anime,) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
        }
    }
}