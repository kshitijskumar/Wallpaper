package com.example.wallpaper.navigation

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.utils.fromJsonToObject
import com.example.wallpaper.utils.toJson
import java.net.URLEncoder

object NavigationDestinations {

    object Home {
        const val route: String = "home"

        fun returnImageClickRoute(image: PhotoResponseModel): String {
            val imageJson = image.toJson()
            return Details.route.replace(Details.WALLPAPER_IMAGE, Uri.encode(imageJson))
        }
    }

    object Details {
        const val WALLPAPER_IMAGE_PARAM = "wallpaper-image"
        const val WALLPAPER_IMAGE = "{$WALLPAPER_IMAGE_PARAM}"
        const val route: String = "details/$WALLPAPER_IMAGE"

        val arguments: List<NamedNavArgument> = listOf(
            navArgument(WALLPAPER_IMAGE_PARAM){ type = NavType.StringType }
        )
    }

}