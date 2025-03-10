package com.example.leboncoinalbumlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import com.example.leboncoinalbumlibrary.presentation.components.AlbumListScreen
import com.example.leboncoinalbumlibrary.presentation.viewmodel.AlbumListViewModel
import com.example.leboncoinalbumlibrary.ui.theme.LeboncoinAlbumLibraryTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AlbumListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeboncoinAlbumLibraryTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.uiState.collectAsState().value

                    AlbumListScreen(
                        state = state,
                        onRetry = viewModel::loadAlbums
                    )
                }
            }
        }
    }
}