package com.example.wallpaper.domain.local.wallpapers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wallpaper.domain.models.PhotoResponseModel

@Database(entities = [PhotoResponseModel::class], version = 1)
@TypeConverters(WallpaperTypeConverter::class)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun wallpaperDao(): WallpaperDao

    companion object {
        @Volatile
        private var INSTANCE: WallpaperDatabase? = null
        fun getWallpaperDatabase(context: Context) : WallpaperDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WallpaperDatabase::class.java,
                    "wallpaper_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}