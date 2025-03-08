package com.example.leboncoinalbumlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import com.example.leboncoinalbumlibrary.presentation.components.AlbumList
import com.example.leboncoinalbumlibrary.presentation.viewmodel.AlbumListUiState
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
//                    AlbumList()
                    val state = viewModel.uiState.collectAsState().value
                    //test vciewModel is working
                    when (state) {
                        is AlbumListUiState.Loading -> Text("Loading...")
                        is AlbumListUiState.Success -> Text("Loaded ${state.albums.size} albums")
                        is AlbumListUiState.Error -> Text("Error: ${state.message}")
                        is AlbumListUiState.Empty -> Text("Found no albums there")
                    }
                }
            }
        }
    }
}