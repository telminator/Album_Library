package com.example.leboncoinalbumlibrary.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.leboncoinalbumlibrary.domain.model.Album

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumList(
    albums: List<Album>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onAlbumClick: (Album) -> Unit
) {
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
                    onClick = { onAlbumClick(album) }
                )
                if (index != albums.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}