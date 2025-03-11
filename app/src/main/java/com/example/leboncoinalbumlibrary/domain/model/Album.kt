package com.example.leboncoinalbumlibrary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)