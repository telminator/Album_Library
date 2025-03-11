package com.example.leboncoinalbumlibrary.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.leboncoinalbumlibrary.data.local.dao.AlbumDao
import com.example.leboncoinalbumlibrary.data.local.entity.AlbumEntity

@Database(
    entities = [AlbumEntity::class],
    version = 1
)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}