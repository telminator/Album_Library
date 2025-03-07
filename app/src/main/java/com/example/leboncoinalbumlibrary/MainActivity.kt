package com.example.leboncoinalbumlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.leboncoinalbumlibrary.presentation.components.AlbumList
import com.example.leboncoinalbumlibrary.ui.theme.LeboncoinAlbumLibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeboncoinAlbumLibraryTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AlbumList()
                }
            }
        }
    }
}