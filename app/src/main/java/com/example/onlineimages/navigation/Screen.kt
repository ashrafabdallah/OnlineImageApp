package com.example.onlineimages.navigation

sealed class Screen(val  rout: String) {
    object HomeScreen:Screen("home")
    object SearchScreen:Screen("search")
}
