package com.example.leboncoinalbumlibrary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.leboncoinalbumlibrary.presentation.viewmodel.AlbumListUiState

@Composable
fun AlbumListScreen(
    state: AlbumListUiState,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is AlbumListUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is AlbumListUiState.Empty -> {
                Text(
                    text = "No albums found",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is AlbumListUiState.Success -> {
                AlbumList(albums = state.albums)
            }

            is AlbumListUiState.Error -> {
                Column (
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: ${state.message}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    // TODO : handle error
                }
            }
        }

    }

}