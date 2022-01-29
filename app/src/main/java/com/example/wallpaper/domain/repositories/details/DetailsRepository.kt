package com.example.wallpaper.domain.repositories.details

import com.example.wallpaper.domain.models.PhotoResponseModel

interface DetailsRepository {

    suspend fun addWallpaperToSaved(photo: PhotoResponseModel)

    suspend fun isWallpaperSaved(photoId: Long) : Boolean

    suspend fun removeWallpaperFromSaved(photo: PhotoResponseModel)
}