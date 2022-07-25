package com.example.onlineimages.data.room_db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.onlineimages.data.model.UnsplashImage

@Dao
interface UnsplashImageDao {
    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImages(): PagingSource<Int, UnsplashImage>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImages(images:List<UnsplashImage>)
    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllImages()
}