package com.example.leboncoinalbumlibrary.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.leboncoinalbumlibrary.domain.model.Album
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumList(
    albums: List<Album>,
    isRefreshing: Boolean,
    isLoadingMore: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    scrollPosition: Int,
    onScrollPositionChanged: (Int) -> Unit,
    onAlbumClick: (Int) -> Unit
) {
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition
    )

    // Save scroll position on change
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                onScrollPositionChanged(index)
            }
    }

    // Load more albums when list end reached
    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = albums.size

            lastVisibleItem >= totalItems - 3 && totalItems > 0
        }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                onLoadMore()
            }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                items = albums,
                key = { _, album -> album.id }
            ) { index, album ->
                AlbumListItem(
                    album = album,
                    onClick = { onAlbumClick(album.id) }
                )
                if (index != albums.size - 1) {
                    HorizontalDivider()
                }
            }

            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}