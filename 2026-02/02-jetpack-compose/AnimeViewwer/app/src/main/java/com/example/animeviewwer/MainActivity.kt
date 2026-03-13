package com.example.animeviewwer

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
import kotlin.collections.filter

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
    Anime(1, "shaman king", 2000, "mech", 450),
    Anime(2, "code geass", 2000, "mech", 450),
    Anime(3, "steins gate", 2000, "mech", 450),
    Anime(4, "frieren", 2000, "mech", 450),
)

class AnimeStateHolder(
    private val allAnime: List<Anime>
) {
    var searchQuery by mutableStateOf("")

    val filteredAnime: List<Anime>
        get() = if (searchQuery.isBlank()) {
            allAnime
        } else {
            allAnime.filter { anime -> anime.title.contains(searchQuery, ignoreCase = true) }
        }

    fun onSearchChange(newValue: String) {
        searchQuery = newValue
    }
}


// ui = f(state)
@Composable
fun AnimeApp() {
    val stateHolder = remember {
        AnimeStateHolder(sampleAnimeList)
    }

    AnimeListScreen(stateHolder.filteredAnime, stateHolder.searchQuery, stateHolder::onSearchChange)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(animeList: List<Anime>, searchQuery: String, onSearchChange: (String) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Anime Viewer") }) } ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search by title") },
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (animeList.isEmpty()) {
                Text("Ничего нету!")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(items = animeList, key = { it.id }) { item ->
                        AnimeCard(item)
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeCard(anime: Anime) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = anime.title, fontWeight = FontWeight.Bold)
                Text("${anime.year} - ${anime.genre}")
                Text("Episodes ${anime.episodes}")
            }
        }
}
