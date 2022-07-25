package com.example.onlineimages.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.onlineimages.presention.screen.home.Homescreen
import com.example.onlineimages.presention.screen.search.Searchscreen

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.rout ){
        composable(route = Screen.HomeScreen.rout){
            Homescreen(navController)
        }
        composable(route=Screen.SearchScreen.rout){
            Searchscreen(navController)
        }
    }

}