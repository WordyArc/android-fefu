package com.example.animeviewwer.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animeviewwer.model.Anime

@Composable
fun AnimeCard(
    anime: Anime,
    onFavoriteClick: (Anime) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = anime.title,
                fontWeight = FontWeight.Bold
            )
            Text("${anime.year} • ${anime.genre}")
            Text("Episodes: ${anime.episodes}")
        }

        IconButton(
            onClick = { onFavoriteClick(anime) }
        ) {
            Text(if (anime.isFavourite) "★" else "☆")
        }
    }
}