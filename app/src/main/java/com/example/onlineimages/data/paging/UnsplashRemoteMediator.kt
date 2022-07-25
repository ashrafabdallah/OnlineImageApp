package com.example.onlineimages.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Insert
import androidx.room.withTransaction
import com.example.onlineimages.data.api.UnsplashApi
import com.example.onlineimages.data.model.UnsplashImage
import com.example.onlineimages.data.model.UnsplashRemoteKeys
import com.example.onlineimages.data.room_db.UnsplashDataBase
import com.example.onlineimages.util.Constant.ITEMS_PER_PAGE
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashLocalDataBase: UnsplashDataBase,
) : RemoteMediator<Int, UnsplashImage>() {
    private val unsplashImageDao = unsplashLocalDataBase.unsplashImageDao()
    private val unsplashRemoteKeysDao = unsplashLocalDataBase.UnsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1

                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND->{
                    val remoteKeys= getRemoteKeyForLastItem(state)
                    val nextpage= remoteKeys?.nextPage
                        ?: return  MediatorResult.Success(
                            endOfPaginationReached = remoteKeys !=null
                        )
                    nextpage
                }


            }
            val response= unsplashApi.getAllImages(page = currentPage, per_page = ITEMS_PER_PAGE)
            val endOfPaginationReached= response.isEmpty()
            val prvPage= if(currentPage==1) null else currentPage-1
            val nextPage= if(endOfPaginationReached) null else currentPage+1
            unsplashLocalDataBase.withTransaction {
                if(loadType==LoadType.REFRESH){
                    unsplashImageDao.deleteAllImages()
                    unsplashRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { unsplashImage ->
                    UnsplashRemoteKeys(
                        id = unsplashImage.id,
                        nextPage = nextPage,
                        prevPage = prvPage
                    )
                }
                unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                unsplashImageDao.insertAllImages(images = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }
    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UnsplashImage>
    ):UnsplashRemoteKeys?{
        return state.pages.firstOrNull{
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { unsplashImage ->
            unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
        }
    }
    private suspend fun getRemoteKeyForLastItem(state:PagingState<Int,UnsplashImage>):UnsplashRemoteKeys?{
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {  unsplashImage->
            unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
        }

    }
}