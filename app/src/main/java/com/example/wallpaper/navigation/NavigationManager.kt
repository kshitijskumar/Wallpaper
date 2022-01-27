package com.example.wallpaper.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wallpaper.domain.models.PhotoResponseModel

class NavigationManager(private val navController: NavHostController) {

    fun navigateToDetailsScreen(photo: PhotoResponseModel) {
        navController.navigate(NavigationDestinations.Home.returnImageClickRoute(photo))
    }

    fun navigateToSearchScreen() {
        navController.navigate(NavigationDestinations.Search.route)
    }
}

@Composable
fun navigationManager() : NavigationManager {
    return NavigationManager(rememberNavController())
}