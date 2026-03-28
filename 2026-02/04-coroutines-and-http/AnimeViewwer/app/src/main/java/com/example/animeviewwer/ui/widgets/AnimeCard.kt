package com.example.animeviewwer.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animeviewwer.model.Anime

@Composable
fun AnimeCard(
    anime: Anime,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = anime.title,
            fontWeight = FontWeight.Bold
        )
        Text("${anime.year} • ${anime.genre}")
        Text("Episodes: ${anime.episodes}")
    }
}