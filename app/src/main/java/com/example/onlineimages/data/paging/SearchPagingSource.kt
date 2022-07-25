package com.example.onlineimages.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.onlineimages.data.api.UnsplashApi
import com.example.onlineimages.data.model.UnsplashImage
import com.example.onlineimages.util.Constant.ITEMS_PER_PAGE

class SearchPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashImage>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
       return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: 1
        return try {
            val response = unsplashApi.searchImages(perPage = ITEMS_PER_PAGE, query = query)
            val endOfPaginationReached = response.images.isEmpty()
            if(response.images.isNotEmpty()){
                LoadResult.Page(
                    data = response.images,
                    prevKey = if(currentPage==1) null else currentPage-1,
                    nextKey = if(endOfPaginationReached) null else currentPage+1


                )
            }else{
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}