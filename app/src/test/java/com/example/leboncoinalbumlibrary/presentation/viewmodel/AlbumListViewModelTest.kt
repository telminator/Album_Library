package com.example.leboncoinalbumlibrary.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.leboncoinalbumlibrary.data.repository.AlbumRepository
import com.example.leboncoinalbumlibrary.domain.model.Album
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumListViewModelTest {
    private lateinit var mockRepository: AlbumRepository
    private lateinit var mockSavedStateHandle: SavedStateHandle
    private lateinit var viewModel: AlbumListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set up test dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)

        // Create mocks
        mockRepository = Mockito.mock(AlbumRepository::class.java)
        mockSavedStateHandle = SavedStateHandle()

        // Create empty list flow for default value
        Mockito.`when`(mockRepository.getAlbums()).thenReturn(flowOf(emptyList()))

        // Create the viewModel
        viewModel = AlbumListViewModel(mockRepository, mockSavedStateHandle)
    }

    @After
    fun tearDown() {
        // Reset dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateIsLoading() = runTest {
        // Initial state should = Loading
        assertTrue(viewModel.uiState.value is AlbumListUiState.Loading)
    }

    @Test
    fun loadAlbumsEmitsSuccessStateWhenDataExists() = runTest {
        // Sample data
        val albums = listOf(
            Album(
                id = 1,
                albumId = 1,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                url = "https://placehold.co/600x600/92c952/white/png",
                thumbnailUrl = "https://placehold.co/150x150/92c952/white/png"
            ),
            Album(
                id = 2,
                albumId = 1,
                title = "eprehenderit est deserunt velit ipsam",
                url = "https://placehold.co/600x600/771796/white/png",
                thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
            )
        )

        // Configure mock repository
        Mockito.`when`(mockRepository.getAlbums()).thenReturn(flowOf(albums))

        // Create new viewModel to trigger init block
        viewModel = AlbumListViewModel(mockRepository, mockSavedStateHandle)

        // Advance the dispatcher to execute coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Check if state was updated
        assertTrue(viewModel.uiState.value is AlbumListUiState.Success)
        val state = viewModel.uiState.value as AlbumListUiState.Success
        assertEquals(2, state.albums.size)
    }

    @Test
    fun refreshAlbumsCallsRepositoryRefresh() = runTest {
        // Call the method
        viewModel.refreshAlbums()

        // Advance the dispatcher
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify repository method was called at least once
        Mockito.verify(mockRepository, Mockito.atLeastOnce()).refreshAlbums()
    }
}