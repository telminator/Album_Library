package com.example.leboncoinalbumlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.example.leboncoinalbumlibrary.presentation.navigation.AppNavGraph
import com.example.leboncoinalbumlibrary.presentation.viewmodel.AlbumListViewModel
import com.example.leboncoinalbumlibrary.ui.theme.LeboncoinAlbumLibraryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: AlbumListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // refresh from server when app returns to foreground
        lifecycle.addObserver(object : androidx.lifecycle.DefaultLifecycleObserver {
            override fun onResume(owner: androidx.lifecycle.LifecycleOwner) {
                viewModel.refreshAlbums()
            }
        })

        setContent {
            LeboncoinAlbumLibraryTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val state = viewModel.uiState.collectAsState().value
                    val isRefreshing = viewModel.isRefreshing.collectAsState().value
                    val isLoadingMore = viewModel.isLoadingMore.collectAsState().value
                    val scrollPosition = viewModel.scrollPosition.collectAsState().value

                    AppNavGraph(
                        navController = navController,
                        state = state,
                        isRefreshing = isRefreshing,
                        isLoadingMore = isLoadingMore,
                        scrollPosition = scrollPosition,
                        onScrollPositionChanged = viewModel::saveScrollPosition,
                        onRefresh = viewModel::refreshAlbums,
                        onLoadMore = viewModel::loadNextPage,
                    )
                }
            }
        }
    }


}