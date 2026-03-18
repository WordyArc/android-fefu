package ru.fefu.anime.browser.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import ru.fefu.anime.browser.model.Anime
import ru.fefu.anime.browser.ui.viewmodel.AnimeUiState

class AnimeListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun searchButton_isDisabled_whenQueryIsBlank() {
        // Arrange
        composeRule.setContent {
            AnimeListScreen(
                state = AnimeUiState(query = ""),
                anime = emptyList(),
                onSearch = {},
                onSearchChange = {},
                onFilterChange = {},
                onToggleFavourite = {}
            )
        }

        // Act
        val searchButton = composeRule.onNodeWithText("Search")

        // Assert
        searchButton.assertIsNotEnabled()
    }

    @Test
    fun animeTitle_isShown_whenListIsNotEmpty() {
        // Arrange
        val anime = Anime(
            id = 1,
            title = "Naruto",
            year = 2002,
            genre = "Action",
            episodes = 220
        )

        composeRule.setContent {
            AnimeListScreen(
                state = AnimeUiState(query = "Naruto"),
                anime = listOf(anime),
                onSearch = {},
                onSearchChange = {},
                onFilterChange = {},
                onToggleFavourite = {}
            )
        }

        // Act
        val searchButton = composeRule.onNodeWithText("Search")
        val titleNode = composeRule.onNodeWithText("Naruto")

        // Assert
        searchButton.assertIsEnabled()
        titleNode.assertIsDisplayed()
    }
}