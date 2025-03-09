package com.example.leboncoinalbumlibrary.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.leboncoinalbumlibrary.domain.model.Album

@Preview
@Composable
fun AlbumListItem(@PreviewParameter(SampleAlbumProvider::class) album: Album){
    Card(
        modifier = Modifier
            .fillMaxWidth())
    {
        Column {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Image")
            }

            Text(
                text = album.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


class SampleAlbumProvider: PreviewParameterProvider<Album> {
    override val values: Sequence<Album>
        get() = sequenceOf(
            Album(
                albumId = 1,
                id = 2,
                title = "accusamus beatae ad facilis cum similique qui sunt",
                url = "https://placehold.co/600x600/771796/white/png",
                thumbnailUrl = "https://placehold.co/150x150/771796/white/png"
            )
        )
}