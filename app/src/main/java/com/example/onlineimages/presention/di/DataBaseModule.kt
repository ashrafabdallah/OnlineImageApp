package com.example.onlineimages.presention.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlineimages.data.room_db.UnsplashDataBase
import com.example.onlineimages.util.Constant.UNSPLASH_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesDataBase(@ApplicationContext context: Context): UnsplashDataBase {
        return Room.databaseBuilder(
            context, UnsplashDataBase::class.java, UNSPLASH_DATABASE
        ).build()

    }
}