package com.example.animeviewwer.ui

import com.example.animeviewwer.data.AnimeRepository
import com.example.animeviewwer.model.Anime
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AnimeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AnimeRepository = mockk()

    private val favouriteAnime = Anime(
        1,
        "Cowboy Bebop",
        1998,
        "Action",
        26,
        true
    )

    @Before
    fun setUp() {
        coEvery { repository.getFavourites() } returns emptyList()
    }

    @Test
    fun `init loads favourites`() = runTest {
        // arrange
        coEvery { repository.getFavourites() } returns listOf(favouriteAnime)

        // act
        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        // assert
        assertEquals(listOf(favouriteAnime), viewModel.uiState.favouriteList)
        coVerify(exactly = 1) { repository.getFavourites() }
    }

    @Test
    fun searchFailure_showsErrors() = runTest {
        // given
        coEvery { repository.searchAnime("bleach") } throws RuntimeException("Network error")
        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        // when
        viewModel.onSearchQueryChange("bleach")
        advanceTimeBy(500)
        advanceUntilIdle()

        // then
        assertEquals("Ошибка загрузки", viewModel.uiState.errorMessage)
        assertTrue(viewModel.uiState.hasSearched)
        assertFalse(viewModel.uiState.isLoading)
        assertEquals(emptyList<Anime>(), viewModel.uiState.animeList)
    }

}