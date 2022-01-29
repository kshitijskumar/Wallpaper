package com.example.wallpaper.domain.repositories.details

import com.example.wallpaper.domain.local.wallpapers.WallpaperDao
import com.example.wallpaper.domain.models.PhotoResponseModel
import com.example.wallpaper.utils.Injector

class DetailsRepositoryImpl(
    private val dao: WallpaperDao = Injector.wallpaperDao
) : DetailsRepository {

    override suspend fun addWallpaperToSaved(photo: PhotoResponseModel) {
        dao.saveWallpaper(photo)
    }

    override suspend fun isWallpaperSaved(photoId: Long): Boolean {
        return dao.isThisImageMarkedAsSave(photoId = photoId)
    }

    override suspend fun removeWallpaperFromSaved(photo: PhotoResponseModel) {
        dao.deleteWallpaper(photo)
    }
}