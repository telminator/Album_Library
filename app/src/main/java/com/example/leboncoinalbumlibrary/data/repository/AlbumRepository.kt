package com.example.leboncoinalbumlibrary.data.repository

import com.example.leboncoinalbumlibrary.data.remote.api.AlbumApiService
import com.example.leboncoinalbumlibrary.data.remote.dto.AlbumDto
import com.example.leboncoinalbumlibrary.domain.model.Album
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(
    private val apiService: AlbumApiService
) {
    suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums().map { it.toDomainModel() }
    }

    private fun AlbumDto.toDomainModel(): Album {
        return Album(
            id = id,
            albumId = albumId,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }

}