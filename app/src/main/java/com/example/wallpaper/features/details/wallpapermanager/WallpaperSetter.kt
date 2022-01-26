package com.example.wallpaper.features.details.wallpapermanager

import android.app.WallpaperManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WallpaperSetter(private val context: Context) {

    private val wallpaperManager: WallpaperManager by lazy {
        WallpaperManager.getInstance(context)
    }

    suspend fun setWallpaper(imageUrl: String): Boolean {
        val imageLoader = ImageLoader(context = context)
        val imageRequest = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = (imageLoader.execute(imageRequest) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        val finalSetResult = cropAndSetWallpaper(bitmap)
        finalSetResult.getOrNull()
        Log.d("WallpaperSet", "result: ${finalSetResult.isSuccess}")

        return finalSetResult.isSuccess
    }

    private suspend fun cropAndSetWallpaper(bitmap: Bitmap): Result<Unit> {
        return kotlin.runCatching {
            withContext(Dispatchers.Default) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val wallpaperHeight = Resources.getSystem().displayMetrics.heightPixels
                    val wallpaperWidth = Resources.getSystem().displayMetrics.widthPixels

                    val startPoint = Point(0, 0)
                    val endPoint = Point(bitmap.width, bitmap.height)

                    if (bitmap.width > wallpaperWidth) {
                        startPoint.x = (bitmap.width - wallpaperWidth)/2
                        endPoint.x = startPoint.x + wallpaperWidth
                    }

                    if (bitmap.height > wallpaperHeight) {
                        startPoint.y = (bitmap.height - wallpaperHeight) / 2
                        endPoint.y = startPoint.y + wallpaperHeight
                    }

                    wallpaperManager.setBitmap(bitmap, Rect(startPoint.x, startPoint.y, endPoint.x, endPoint.y), false)
                } else {
                    wallpaperManager.setBitmap(bitmap)
                }
            }

        }
    }
}