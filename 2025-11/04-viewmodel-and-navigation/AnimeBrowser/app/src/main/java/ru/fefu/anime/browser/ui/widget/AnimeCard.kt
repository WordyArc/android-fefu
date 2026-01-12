package ru.fefu.anime.shelf.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.fefu.anime.shelf.model.Anime

@Composable
fun AnimeCard(
    anime: Anime,
    isFavourite: Boolean,
    onToggleFavourite: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = anime.title, fontWeight = FontWeight.Bold)
            Text("${anime.year} - ${anime.genre}")
            Text("Episodes: ${anime.episodes}")
        }

        Text(
            text = if (isFavourite) "+" else "-",
            modifier = Modifier.clickable(onClick = onToggleFavourite)
        )
    }
}