package com.example.wallpaper.utils

import android.content.Context
import com.example.wallpaper.BuildConfig
import com.example.wallpaper.domain.local.wallpapers.WallpaperDao
import com.example.wallpaper.domain.local.wallpapers.WallpaperDatabase
import com.example.wallpaper.domain.network.AuthInterceptor
import com.example.wallpaper.domain.network.PexelsApiService
import com.example.wallpaper.domain.repositories.details.DetailsRepository
import com.example.wallpaper.domain.repositories.details.DetailsRepositoryImpl
import com.example.wallpaper.domain.repositories.home.HomeRepository
import com.example.wallpaper.domain.repositories.home.HomeRepositoryImpl
import com.example.wallpaper.domain.repositories.search.SearchRepository
import com.example.wallpaper.domain.repositories.search.SearchRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

object Injector {

    var provideContext: ((String) -> Context)? = null

    val dispatchers: DispatcherProvider by lazy {
        object : DispatcherProvider {
            override val ioDispatcher: CoroutineContext
                get() = Dispatchers.IO
            override val mainDispatcher: CoroutineContext
                get() = Dispatchers.Main
        }
    }

    val gson: Gson
        get() = GsonBuilder().create()

    private val authInterceptor: Interceptor by lazy {
        AuthInterceptor()
    }

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addNetworkInterceptor(authInterceptor)
            .build()


    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BuildConfig.PEXEL_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val pexelApiService: PexelsApiService
        get() = retrofit.create(PexelsApiService::class.java)

    val homeRepository: HomeRepository
        get() = HomeRepositoryImpl()

    val searchRepository: SearchRepository
        get() = SearchRepositoryImpl()

    private val wallpaperDatabase by lazy {
        WallpaperDatabase.getWallpaperDatabase(provideContext!!.invoke("for database init"))
    }

    val wallpaperDao: WallpaperDao by lazy {
        wallpaperDatabase.wallpaperDao()
    }

    val detailsRepository: DetailsRepository
        get() = DetailsRepositoryImpl(wallpaperDao)

}

interface DispatcherProvider {
    val ioDispatcher: CoroutineContext
    val mainDispatcher: CoroutineContext
}