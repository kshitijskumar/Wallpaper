package com.example.wallpaper.domain.local.wallpapers

import androidx.room.TypeConverter
import com.example.wallpaper.domain.models.PhotoSourceModel
import com.example.wallpaper.utils.fromJsonToObject
import com.example.wallpaper.utils.toJson

class WallpaperTypeConverter {

    @TypeConverter
    fun fromWallpaperSrcToString(photoSrc: PhotoSourceModel) : String {
        return photoSrc.toJson()
    }

    @TypeConverter
    fun fromStringToWallpaper(photoSrcString: String?) : PhotoSourceModel {
        return photoSrcString?.fromJsonToObject() ?: PhotoSourceModel()
    }
}