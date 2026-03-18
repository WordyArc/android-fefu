package ru.fefu.anime.browser.ui.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import ru.fefu.anime.browser.FakeAnimeRepository
import ru.fefu.anime.browser.MainDispatcherRule
import ru.fefu.anime.browser.data.AnimeRepository
import ru.fefu.anime.browser.model.Anime
import ru.fefu.anime.browser.model.AnimeFilter

class AnimeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val naruto = Anime(
        id = 1,
        title = "Naruto",
        year = 2002,
        genre = "Action",
        episodes = 220
    )

    private val bleach = Anime(
        id = 2,
        title = "Bleach",
        year = 2004,
        genre = "Action",
        episodes = 366
    )

    @Test
    fun `onQueryChange updates query and clears error`() = runTest {
        // Arrange
        val repository = FakeAnimeRepository()
        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        viewModel.search()
        assertNotNull(viewModel.uiState.errorMessage)

        // Act
        viewModel.onQueryChange("Naruto")

        // Assert
        assertEquals("Naruto", viewModel.uiState.query)
        assertNull(viewModel.uiState.errorMessage)
    }

    @Test
    fun `search with blank query sets error and keeps loading false`() = runTest {
        // Arrange
        val repository = FakeAnimeRepository()
        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        // Act
        viewModel.search()

        // Assert
        assertFalse(viewModel.uiState.isLoading)
        assertNotNull(viewModel.uiState.errorMessage)
        assertTrue(viewModel.visibleAnime.isEmpty())
    }

    @Test
    fun `search success updates visibleAnime and calls repository`() = runTest {
        // Arrange
        val repository = mockk<AnimeRepository>()
        coEvery { repository.getFavourites() } returns emptyList()
        coEvery { repository.searchAnime("Naruto") } returns listOf(naruto, bleach)

        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        // Act
        viewModel.onQueryChange("Naruto")
        viewModel.search()
        advanceUntilIdle()

        // Assert
        assertFalse(viewModel.uiState.isLoading)
        assertNull(viewModel.uiState.errorMessage)
        assertEquals(listOf(naruto, bleach), viewModel.visibleAnime)
        coVerify(exactly = 1) { repository.searchAnime("Naruto") }
    }

    @Test
    fun `onFilterChange to favourites shows only favourite items`() = runTest {
        // Arrange
        val repository = FakeAnimeRepository().apply {
            favourites.add(naruto)
            searchResult = listOf(naruto, bleach)
        }
        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("shonen")
        viewModel.search()
        advanceUntilIdle()

        // Act
        viewModel.onFilterChange(AnimeFilter.FAVOURITES)

        // Assert
        assertEquals(AnimeFilter.FAVOURITES, viewModel.uiState.filter)
        assertEquals(listOf(naruto), viewModel.visibleAnime)
    }

    @Test
    fun `onToggleFavourites adds anime to favourites and calls repository`() = runTest {
        // Arrange
        val repository = mockk<AnimeRepository>()
        coEvery { repository.getFavourites() } returns emptyList()
        coEvery { repository.searchAnime("Naruto") } returns listOf(naruto)
        coEvery { repository.addFavourite(naruto) } returns Unit

        val viewModel = AnimeViewModel(repository)
        advanceUntilIdle()

        viewModel.onQueryChange("Naruto")
        viewModel.search()
        advanceUntilIdle()

        // Act
        viewModel.onToggleFavourites(naruto.id)
        advanceUntilIdle()

        // Assert
        assertTrue(naruto.id in viewModel.uiState.favourites)
        coVerify(exactly = 1) { repository.addFavourite(naruto) }
    }
}