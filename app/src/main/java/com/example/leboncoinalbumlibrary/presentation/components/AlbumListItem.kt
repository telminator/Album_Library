package com.example.leboncoinalbumlibrary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.leboncoinalbumlibrary.domain.model.Album

@Preview
@Composable
fun AlbumListItem(@PreviewParameter(SampleAlbumProvider::class) album: Album) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )
    {
        Column {
//            AsyncImage(
//                model = album.thumbnailUrl,
//                contentDescription = "Album thumbnail",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1f)
//            )


            Text(
                text = album.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


class SampleAlbumProvider : PreviewParameterProvider<Album> {
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