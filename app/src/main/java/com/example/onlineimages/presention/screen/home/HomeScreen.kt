package com.example.onlineimages.presention.screen.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.onlineimages.navigation.Screen
import com.example.onlineimages.presention.screen.common.ListContent
import com.example.onlineimages.presention.viewmodel.HomeViewModel

@Composable
fun Homescreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.allImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.SearchScreen.rout)
                }
            )
        },
        content = {
            ListContent(items = getAllImages)
        }
    )
}