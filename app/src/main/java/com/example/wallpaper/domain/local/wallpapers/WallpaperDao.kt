package com.example.wallpaper.domain.local.wallpapers

import androidx.room.*
import com.example.wallpaper.domain.models.PhotoResponseModel

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWallpaper(wallpaper: PhotoResponseModel)

    @Delete
    suspend fun deleteWallpaper(wallpaper: PhotoResponseModel)

    @Query("SELECT EXISTS(SELECT 1 FROM wallpaper_table WHERE id=:photoId LIMIT 1)")
    suspend fun isThisImageMarkedAsSave(photoId: Long) : Boolean

}