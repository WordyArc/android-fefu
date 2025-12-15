package ru.fefu.anime.shelf.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.fefu.anime.shelf.model.Anime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailScreen(
    anime: Anime,
    isFavourite: Boolean,
    onBack: () -> Unit,
    onToggleFavourite: () -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Detail") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        ) {
            TextButton(onClick = onBack) { Text("Назад") }

            Spacer(Modifier.height(12.dp))

            Text(text = anime.title, fontWeight = FontWeight.Bold)
            Text("${anime.year} - ${anime.genre}")
            Text("Episodes: ${anime.episodes}")

            Spacer(Modifier.height(12.dp))

            Row {
                Text("Favourite: ")
                Text(
                    text = if (isFavourite) "+" else "-",
                    modifier = Modifier.clickable(onClick = onToggleFavourite)
                )
            }
        }
    }
}
