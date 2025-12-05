package com.lufthansa.presentation.viewmodel

import com.lufthansa.domain.model.Gif
import com.lufthansa.domain.usecase.GetTrendingUseCase
import com.lufthansa.domain.usecase.SearchGifsUseCase
import com.lufthansa.presentation.ui.UiState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GiphyViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var getTrending: GetTrendingUseCase
    private lateinit var searchGifs: SearchGifsUseCase
    private lateinit var viewModel: GiphyViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        getTrending = mockk()
        searchGifs = mockk()

        coEvery { getTrending() } returns Result.success(listOf(dummyGif("trending")))

        viewModel = GiphyViewModel(getTrending, searchGifs)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun dummyGif(id: String) = Gif(
        id = id,
        title = "GIF $id",
        username = "user$id",
        previewUrl = "https://example.com/$id-preview.gif",
        fullUrl = "https://example.com/$id.gif",
        width = 100,
        height = 100
    )


    @Test
    fun `retry loads trending when query is blank`() = runTest(dispatcher) {
        coEvery { getTrending() } returns Result.success(listOf(dummyGif("retry")))

        viewModel.retry()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Success)

        coVerify(atLeast = 1) { getTrending() }
    }

    @Test
    fun `retry performs search when query is not blank`() = runTest(dispatcher) {
        coEvery { searchGifs("dog") } returns Result.success(listOf(dummyGif("dog")))

        viewModel.onQueryChanged("dog")
        advanceTimeBy(400)
        advanceUntilIdle()

        viewModel.retry()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Success)

        coVerify(atLeast = 1) { searchGifs("dog") }
    }
}
