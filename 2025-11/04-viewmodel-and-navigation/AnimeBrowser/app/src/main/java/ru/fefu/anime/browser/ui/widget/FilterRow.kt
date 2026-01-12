package ru.fefu.anime.shelf.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.fefu.anime.shelf.model.AnimeFilter

@Composable
fun FilterRow(
    filter: AnimeFilter,
    onFilterChange: (AnimeFilter) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(
            onClick = { onFilterChange(AnimeFilter.ALL) },
            enabled = filter != AnimeFilter.ALL
        ) { Text(if (filter == AnimeFilter.ALL) "• All" else "All") }

        TextButton(
            onClick = { onFilterChange(AnimeFilter.FAVOURITES) },
            enabled = filter != AnimeFilter.FAVOURITES
        ) { Text(if (filter == AnimeFilter.FAVOURITES) "• Favourites" else "Favourites") }
    }
}