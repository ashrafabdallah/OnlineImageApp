package com.example.onlineimages.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.onlineimages.data.api.UnsplashApi
import com.example.onlineimages.data.model.UnsplashImage
import com.example.onlineimages.data.paging.SearchPagingSource
import com.example.onlineimages.data.paging.UnsplashRemoteMediator
import com.example.onlineimages.data.room_db.UnsplashDataBase
import com.example.onlineimages.util.Constant.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashLocalDataBase: UnsplashDataBase
) {

    fun getAllImages():Flow<PagingData<UnsplashImage>>{
        val pagingSourceFactory = {unsplashLocalDataBase.unsplashImageDao().getAllImages()}
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(unsplashApi = unsplashApi, unsplashLocalDataBase = unsplashLocalDataBase),
            pagingSourceFactory = pagingSourceFactory

        ).flow
    }

    fun getSearchImage(query:String): Flow<PagingData<UnsplashImage>>{
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(query = query, unsplashApi = unsplashApi)
            }

        ).flow
    }


}