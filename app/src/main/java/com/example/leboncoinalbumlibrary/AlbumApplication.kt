package com.example.leboncoinalbumlibrary

import android.app.Application
import android.util.Log
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AlbumApplication : Application(){

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        // Set the default ImageLoader
        Log.e("ImageLoader", "Setting custom image loader")
        Coil.setImageLoader(imageLoader)
    }
}