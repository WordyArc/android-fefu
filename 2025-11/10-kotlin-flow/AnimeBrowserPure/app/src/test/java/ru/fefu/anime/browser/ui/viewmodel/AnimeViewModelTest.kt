package ru.fefu.anime.browser.ui.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import ru.fefu.anime.browser.FakeAnimeRepository
import ru.fefu.anime.browser.MainDispatcherRule
import ru.fefu.anime.browser.data.AnimeRepository
import ru.fefu.anime.browser.model.Anime

class AnimeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val naruto = Anime(1, "Naruto", 2002, "Action", 500)
    private val bleach = Anime(2, "Bleach", 2000, "Action", 300)

    @Test
    fun `onQueryChange updates query and clear error`() = runTest {
        // arrange
        val repository = FakeAnimeRepository()
        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        viewModel.search()
        assertNotNull(viewModel.uiState.errorMessage)

        // act
        viewModel.onQueryChange("Naruto")

        // assert
        assertEquals("Naruto", viewModel.uiState.query)
        assertNull(viewModel.uiState.errorMessage)
    }


    @Test
    fun `search with blunk query sets error and keeps loading false`() = runTest {
        // arrange
        val repository = FakeAnimeRepository()
        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        // act
        viewModel.search()

        // assert
        assertFalse(viewModel.uiState.isLoading)
        assertNotNull(viewModel.uiState.errorMessage)
    }

    @Test
    fun `search success updates visibleAnime and calls repository`() = runTest {
        val repository = mockk<AnimeRepository>()
        coEvery { repository.getFavourites() } returns emptyList()
        coEvery { repository.searchAnime("Naruto") } returns listOf(naruto, bleach)

        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("Naruto")
        viewModel.search()
        advanceUntilIdle()

        assertFalse(viewModel.uiState.isLoading)
        assertNull(viewModel.uiState.errorMessage)
        assertEquals(listOf(naruto, bleach), viewModel.uiState.visibleAnime)
        coVerify(exactly = 1) { repository.searchAnime("Naruto") }
    }

}