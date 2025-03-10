package com.example.leboncoinalbumlibrary.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.leboncoinalbumlibrary.domain.model.Album

@Composable
fun AlbumList(albums: List<Album>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = albums,
            key = { _, album -> album.id }
        ) { index, album ->
            AlbumListItem(album)
            if (index != albums.size - 1) {
                HorizontalDivider()
            }
        }
    }
}