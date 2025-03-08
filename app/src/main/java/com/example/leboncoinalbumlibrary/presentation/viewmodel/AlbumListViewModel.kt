package com.example.leboncoinalbumlibrary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leboncoinalbumlibrary.data.repository.AlbumRepository
import com.example.leboncoinalbumlibrary.domain.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumListUiState>(AlbumListUiState.Loading)
    val uiState: StateFlow<AlbumListUiState> = _uiState.asStateFlow()

    init {
        loadAlbums()
    }

    fun loadAlbums() {
        viewModelScope.launch {
            _uiState.value = AlbumListUiState.Loading

            try {
                val albums = repository.getAlbums()
                _uiState.value = if (albums.isEmpty()) {
                    AlbumListUiState.Empty
                } else {
                    AlbumListUiState.Success(albums)
                }
            } catch (e: Exception) {
                _uiState.value = AlbumListUiState.Error(e.message ?: "Something wrong happened")
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