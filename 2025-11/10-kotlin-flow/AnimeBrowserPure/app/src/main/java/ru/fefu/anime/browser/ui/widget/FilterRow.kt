package ru.fefu.anime.browser.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.fefu.anime.browser.model.AnimeFilter

@Composable
fun FilterRow(
    filter: AnimeFilter,
    onFilterChange: (AnimeFilter) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = filter == AnimeFilter.ALL,
            onClick = { onFilterChange(AnimeFilter.ALL) },
            label = { Text("Все") }
        )

        FilterChip(
            selected = filter == AnimeFilter.FAVOURITES,
            onClick = { onFilterChange(AnimeFilter.FAVOURITES) },
            label = { Text("Избранное") }
        )
    }
}