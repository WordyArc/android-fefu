package ru.fefu.anime.browser.ui.screen

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import ru.fefu.anime.browser.ui.viewmodel.AnimeUiState

class AnimeListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun searchButton_isDisabled_whenQueryIsBlank() {
        composeRule.setContent {
            AnimeListScreen(
                state = AnimeUiState(query = ""),
                onSearch = { },
                onSearchChange = { },
                onFilterChange = { },
                onToggleFavourite = { },
            )
        }

        val searchButton = composeRule.onNodeWithText("Найти")

        searchButton.assertIsNotEnabled()
    }

}