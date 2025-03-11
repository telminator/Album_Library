package com.example.leboncoinalbumlibrary.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leboncoinalbumlibrary.presentation.components.AlbumDetailScreen
import com.example.leboncoinalbumlibrary.presentation.components.AlbumListScreen
import com.example.leboncoinalbumlibrary.presentation.viewmodel.AlbumDetailViewModel
import com.example.leboncoinalbumlibrary.presentation.viewmodel.AlbumListUiState

@Composable
fun AppNavGraph(
    navController: NavHostController,
    state: AlbumListUiState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AlbumListRoute
    ) {
        composable<AlbumListRoute> {
            AlbumListScreen(
                state = state,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                onAlbumClick = { albumId ->
                    navController.navigate(AlbumDetailRoute(albumId))
                }
            )
        }

        composable<AlbumDetailRoute> {
            val viewModel: AlbumDetailViewModel = hiltViewModel()
            val album by viewModel.albumState.collectAsState()

            album?.let {
                AlbumDetailScreen(
                    album = it,
                    onBackPressed = { navController.popBackStack() }
                )
            } ?: run {
                // Show loading or error state
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}