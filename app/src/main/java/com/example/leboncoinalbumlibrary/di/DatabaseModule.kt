package com.example.leboncoinalbumlibrary.di

import android.content.Context
import androidx.room.Room
import com.example.leboncoinalbumlibrary.data.local.dao.AlbumDao
import com.example.leboncoinalbumlibrary.data.local.database.AlbumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlbumDatabase {
        return Room.databaseBuilder(
            context,
            AlbumDatabase::class.java,
            "album_database"
        ).build()
    }

    @Provides
    fun provideAlbumDao(database: AlbumDatabase): AlbumDao {
        return database.albumDao()
    }
}