package com.example.onlineimages.presention.screen.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.onlineimages.presention.screen.common.ListContent
import com.example.onlineimages.presention.viewmodel.SearchViewModel

@Composable
fun Searchscreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()
    Scaffold(topBar = {
        SearchWidget(
            onSearchClose = {
                navController.popBackStack()
            },
            query = searchQuery,
            onTextChange = { searchViewModel.updateSearchQuery(query = it) },
            onSearchClicked = {
                searchViewModel.executeSearch(query = it)
            },
            navController = navController
        )

    },
        content = {
            ListContent(items = searchedImages)
        }
    )


}