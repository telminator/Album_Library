package com.example.leboncoinalbumlibrary.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object AlbumListRoute

@Serializable
data class AlbumDetailRoute(val albumId: Int)
