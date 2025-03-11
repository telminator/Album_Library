package com.example.leboncoinalbumlibrary.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leboncoinalbumlibrary.data.repository.AlbumRepository
import com.example.leboncoinalbumlibrary.domain.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val repository: AlbumRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumListUiState>(AlbumListUiState.Loading)
    val uiState: StateFlow<AlbumListUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    //paginatin
    private val _currentPage = MutableStateFlow(savedStateHandle.get<Int>("currentPage") ?: 0)
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    // scroll position
    private val _scrollPosition = savedStateHandle.get<Int>("SCROLL_POSITION") ?: 0
    private val scrollPositionFlow = MutableStateFlow(_scrollPosition)
    val scrollPosition: StateFlow<Int> = scrollPositionFlow.asStateFlow()

    init {
        loadAlbums()
        refreshAlbums()
    }

    fun loadAlbums() {
        repository.getAlbums()
            .onEach { albums ->
                if (albums.isEmpty()) {
                    if (_uiState.value !is AlbumListUiState.Loading) {
                        _uiState.value = AlbumListUiState.Empty
                    }
                } else {
                    _uiState.value = AlbumListUiState.Success(albums)
                }
            }
            .catch { e ->
                if (_uiState.value !is AlbumListUiState.Success) {
                    _uiState.value = AlbumListUiState.Error(e.message ?: "Something wrong happened")
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadNextPage() {
        if (_isLoadingMore.value) return

        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                val nextPage = _currentPage.value + 1
                val newAlbums = repository.getAlbums(page = nextPage).first()

                if (newAlbums.isNotEmpty()) {
                    _currentPage.value = nextPage
                    savedStateHandle["currentPage"] = nextPage

                    _albums.value += newAlbums
                    _uiState.value = AlbumListUiState.Success(_albums.value)
                }
            } catch (e: Exception) {
                // Just log the error, don't change UI state if we already have data
                Log.e("AlbumListViewModel", "Error loading more pages: ${e.message}")
            } finally {
                _isLoadingMore.value = false
            }
        }
    }

    fun refreshAlbums() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                repository.refreshAlbums()
            } catch (e: Exception) {
                // We'll show error only if we don't have cached data
                if (_uiState.value !is AlbumListUiState.Success) {
                    _uiState.value = AlbumListUiState.Error(e.message ?: "Unknown error")
                }
            } finally {
                // Make sure to reset the refreshing state when done
                _isRefreshing.value = false
            }
        }
    }

    fun saveScrollPosition(position: Int) {
        savedStateHandle["SCROLL_POSITION"] = position
        scrollPositionFlow.value = position
    }
}

sealed class AlbumListUiState {
    object Loading : AlbumListUiState()
    object Empty : AlbumListUiState()
    data class Success(val albums: List<Album>) : AlbumListUiState()
    data class Error(val message: String) : AlbumListUiState()
}