package com.example.leboncoinalbumlibrary.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.leboncoinalbumlibrary.data.repository.AlbumRepository
import com.example.leboncoinalbumlibrary.domain.model.Album
import com.example.leboncoinalbumlibrary.presentation.navigation.AlbumDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val repository: AlbumRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val albumId = savedStateHandle.toRoute<AlbumDetailRoute>().albumId
    private val _albumState = MutableStateFlow<Album?>(null)
    val albumState: StateFlow<Album?> = _albumState.asStateFlow()

    init {
        loadAlbum()
    }

    private fun loadAlbum() {
        viewModelScope.launch {
            repository.getAlbums()
                .collect { albums ->
                    val album = albums.find { it.id == albumId }
                    _albumState.value = album
                }
        }
    }
}