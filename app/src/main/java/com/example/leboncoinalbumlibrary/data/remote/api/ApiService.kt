package com.example.leboncoinalbumlibrary.data.remote.api

import com.example.leboncoinalbumlibrary.data.remote.dto.AlbumDto
import retrofit2.http.GET

interface ApiService {
    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<AlbumDto>
}