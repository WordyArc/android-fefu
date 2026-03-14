package com.example.animeviewwer

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
import androidx.compose.material3.Button
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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

data class AnimeDetails(
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val episodes: Int,
    val studio: String,
    val description: String,
    val rating: Double,
)

val sampleAnimeList = listOf(
    Anime(1, "Shaman King", 2001, "Adventure", 64),
    Anime(2, "Code Geass", 2006, "Mecha", 50),
    Anime(3, "Steins;Gate", 2011, "Sci-Fi", 24),
    Anime(4, "Frieren", 2023, "Fantasy", 28),
)

val sampleAnimeDetailsList = listOf(
    AnimeDetails(
        id = 1,
        title = "Shaman King",
        year = 2001,
        genre = "Adventure",
        episodes = 64,
        studio = "Xebec",
        description = "История про турнир шаманов и путь Йо Асакуры.",
        rating = 7.8
    ),
    AnimeDetails(
        id = 2,
        title = "Code Geass",
        year = 2006,
        genre = "Mecha",
        episodes = 50,
        studio = "Sunrise",
        description = "Политический конфликт, меха и сила Geass.",
        rating = 8.7
    ),
    AnimeDetails(
        id = 3,
        title = "Steins;Gate",
        year = 2011,
        genre = "Sci-Fi",
        episodes = 24,
        studio = "White Fox",
        description = "История о путешествиях во времени и последствиях выбора.",
        rating = 8.8
    ),
    AnimeDetails(
        id = 4,
        title = "Frieren",
        year = 2023,
        genre = "Fantasy",
        episodes = 28,
        studio = "Madhouse",
        description = "Спокойное путешествие после завершения великого приключения.",
        rating = 9.1
    ),
)

fun getAnimeDetailsById(id: Int): AnimeDetails? {
    return sampleAnimeDetailsList.find { it.id == id }
}


data class AnimeListUiState(
    val searchQuery: String = "",
    val animeList: List<Anime> = sampleAnimeList
)
class AnimeViewModel : ViewModel() {

    var uiState by mutableStateOf(AnimeListUiState())
        private set

    fun onSearchChange(newValue: String) {
        uiState = uiState.copy(
            searchQuery = newValue,
            animeList = filterAnime(newValue)
        )
    }

    fun getAnimeDetailsByid(id: Int): AnimeDetails? {
        return sampleAnimeDetailsList.find { it.id == id }
    }

    private fun filterAnime(query: String): List<Anime> {
        if (query.isBlank()) return sampleAnimeList

        return sampleAnimeList.filter { anime ->
            anime.title.contains(query, ignoreCase = true)
        }
    }
}


object AnimeRoutes {
    const val LIST_ROUTE = "anime_list"
    const val DETAILS_ROUTE = "anime_details"

    const val ANIME_ID_ARG = "animeId"
    const val DETAILS_ROUTE_PATTERN = "$DETAILS_ROUTE/{$ANIME_ID_ARG}"
    fun details(animeId: Int): String = "$DETAILS_ROUTE/$animeId"
}


@Composable
fun AnimeApp() {
    val animeViewModel: AnimeViewModel = viewModel()
    val uiState = animeViewModel.uiState
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AnimeRoutes.LIST_ROUTE) {
        composable(AnimeRoutes.LIST_ROUTE) {
            AnimeListScreen(
                animeList = uiState.animeList,
                searchQuery = uiState.searchQuery,
                onSearchChange = animeViewModel::onSearchChange,
                onAnimeClick = { animeId -> navController.navigate(AnimeRoutes.details(animeId)) } // anime_details/4
            )
        }

        composable(route = AnimeRoutes.DETAILS_ROUTE_PATTERN, arguments = listOf(
            navArgument(AnimeRoutes.ANIME_ID_ARG) { type = NavType.IntType }
        )) { backStackEntry ->
            val animeId: Int? = backStackEntry.arguments?.getInt(AnimeRoutes.ANIME_ID_ARG)

            AnimeDetailsScreen(
                animeDetails = animeId?.let(animeViewModel::getAnimeDetailsByid),
                onBackClick = { navController.navigateUp() }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    animeList: List<Anime>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAnimeClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Anime Viewer") }) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
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
                        AnimeCard(
                            anime = item,
                            onClick = { onAnimeClick(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeCard(
    anime: Anime,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = anime.title, fontWeight = FontWeight.Bold)
            Text("${anime.year} • ${anime.genre}")
            Text("Episodes: ${anime.episodes}")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailsScreen(
    animeDetails: AnimeDetails?,
    onBackClick: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Anime Details") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(onClick = onBackClick) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (animeDetails == null) {
                Text("Детали не найдены")
            } else {
                Text(
                    text = animeDetails.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Year: ${animeDetails.year}")
                Text("Genre: ${animeDetails.genre}")
                Text("Episodes: ${animeDetails.episodes}")
                Text("Studio: ${animeDetails.studio}")
                Text("Rating: ${animeDetails.rating}")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(animeDetails.description)
            }
        }
    }
}