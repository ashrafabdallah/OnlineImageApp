package com.example.onlineimages.data.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onlineimages.data.model.UnsplashImage
import com.example.onlineimages.data.model.UnsplashRemoteKeys
import com.example.onlineimages.data.room_db.dao.UnsplashImageDao
import com.example.onlineimages.data.room_db.dao.UnsplashRemoteKeysDao

@Database(entities = [UnsplashImage::class,UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDataBase :RoomDatabase(){
    abstract fun unsplashImageDao():UnsplashImageDao
    abstract fun UnsplashRemoteKeysDao():UnsplashRemoteKeysDao
}