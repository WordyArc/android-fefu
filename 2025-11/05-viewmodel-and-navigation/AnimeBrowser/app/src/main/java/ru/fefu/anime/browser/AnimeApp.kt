package ru.fefu.anime.browser

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.fefu.anime.shelf.ui.screen.AnimeDetailScreen
import ru.fefu.anime.shelf.ui.screen.AnimeListScreen
import ru.fefu.anime.shelf.ui.viewmodel.AnimeViewModel
import kotlin.let


sealed class AnimeRoute(val route: String) {

    data object List : AnimeRoute("list")

    data object Detail : AnimeRoute("detail/{id}") {
        const val ARG_ID = "id"
        fun createRoute(id: Int): String = "detail/$id"
    }
}

@Composable
fun AnimeApp() {
    val holder: AnimeViewModel = viewModel()
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = AnimeRoute.List.route) {
        composable(route = AnimeRoute.List.route) {
            val state = holder.uiState

            AnimeListScreen(
                state = state,
                anime = holder.visibleAnime,
                onSearchChange = holder::onQueryChange,
                onFilterChange = holder::onFilterChange,
                onToggleFavourite = holder::onToggleFavourites,
                onSelect = { navController.navigate(AnimeRoute.Detail.createRoute(it)) }
            )
        }

        composable(
            route = AnimeRoute.Detail.route,
            arguments = listOf(navArgument(AnimeRoute.Detail.ARG_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(AnimeRoute.Detail.ARG_ID)
            val anime = id?.let(holder::animeById)

            if (anime == null) {
                Text("Anime not found")
            } else {
                val state = holder.uiState
                AnimeDetailScreen(
                    anime = anime,
                    isFavourite = anime.id in state.favourites,
                    onBack = { navController.popBackStack() },
                    onToggleFavourite = { holder.onToggleFavourites(anime.id) })
            }

        }
    }
}