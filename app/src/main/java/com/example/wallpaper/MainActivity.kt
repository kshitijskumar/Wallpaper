package com.example.wallpaper

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.features.details.DetailsScreen
import com.example.wallpaper.features.home.HomeScreen
import com.example.wallpaper.features.search.SearchScreen
import com.example.wallpaper.navigation.NavigationDestinations
import com.example.wallpaper.navigation.NavigationDestinations.Details.WALLPAPER_IMAGE
import com.example.wallpaper.navigation.NavigationDestinations.Details.WALLPAPER_IMAGE_PARAM
import com.example.wallpaper.ui.theme.WallpaperTheme
import com.example.wallpaper.utils.fromJsonToObject
import com.example.wallpaper.utils.fromPathWithBracesToPathWithoutBraces

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = NavigationDestinations.Home.route) {
                        composable(
                            route = NavigationDestinations.Home.route
                        ) {
                            HomeScreen(
                                onImageClick = {
                                    navController.navigate(NavigationDestinations.Home.returnImageClickRoute(it))
                                },
                                onSearchBarClick = {
                                    navController.navigate(NavigationDestinations.Search.route)
                                }
                            )
                        }

                        composable(
                            route = NavigationDestinations.Details.route,
                            arguments = NavigationDestinations.Details.arguments
                        ) {
                            val outputString = Uri.decode(it.arguments?.getString(WALLPAPER_IMAGE_PARAM))
                            val photoModel = outputString?.fromJsonToObject<PhotoResponseModel>() ?: return@composable
                            DetailsScreen(photoModel)
                        }

                        composable(
                            route = NavigationDestinations.Search.route,
                            arguments = NavigationDestinations.Search.arguments
                        ) {
                            SearchScreen()
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WallpaperTheme {
        Greeting("Android")
    }
}