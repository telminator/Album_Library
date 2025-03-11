package com.example.leboncoinalbumlibrary.data.repository

import com.example.leboncoinalbumlibrary.data.local.dao.AlbumDao
import com.example.leboncoinalbumlibrary.data.local.entity.AlbumEntity
import com.example.leboncoinalbumlibrary.data.remote.api.AlbumApiService
import com.example.leboncoinalbumlibrary.data.remote.dto.AlbumDto
import com.example.leboncoinalbumlibrary.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(
    private val apiService: AlbumApiService,
    private val dao: AlbumDao
) {

    // Get local album data
    fun getAlbums(): Flow<List<Album>> {
        return dao.getAllAlbums().map { albumEntities ->
            albumEntities.map { it.toDomain() }
        }
    }

    // refresh local persisted data from remote
    suspend fun refreshAlbums() {
        try {
            val albums = apiService.getAlbums()
            dao.insertAlbums(albums.map { it.toEntity() })
        } catch (e: Exception) {
            // TODO Handle. Just show something happened error to user, since we already have local data
            throw e
        }
    }

    private fun AlbumEntity.toDomain(): Album {
        return Album(
            id = id,
            albumId = albumId,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }

    private fun AlbumDto.toEntity(): AlbumEntity {
        return AlbumEntity(
            id = id,
            albumId = albumId,
            title = title,
            url = url,
            thumbnailUrl = thumbnailUrl
        )
    }
}