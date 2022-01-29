package com.example.wallpaper

import android.app.Application
import android.util.Log
import com.example.wallpaper.utils.Injector

class WallpaperApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.provideContext = { reason ->
            Log.d("ContextProvider", "context for : $reason")
            this
        }
    }
}