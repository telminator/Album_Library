package com.example.leboncoinalbumlibrary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leboncoinalbumlibrary.data.repository.AlbumRepository
import com.example.leboncoinalbumlibrary.domain.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumListUiState>(AlbumListUiState.Loading)
    val uiState: StateFlow<AlbumListUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

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
}

sealed class AlbumListUiState {
    object Loading : AlbumListUiState()
    object Empty : AlbumListUiState()
    data class Success(val albums: List<Album>) : AlbumListUiState()
    data class Error(val message: String) : AlbumListUiState()
}