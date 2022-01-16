package com.example.wallpaper.utils

import com.example.wallpaper.BuildConfig
import com.example.wallpaper.domain.network.AuthInterceptor
import com.example.wallpaper.domain.network.PexelsApiService
import com.example.wallpaper.domain.repositories.home.HomeRepository
import com.example.wallpaper.domain.repositories.home.HomeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

object Injector {

    val dispatchers: DispatcherProvider by lazy {
        object : DispatcherProvider {
            override val ioDispatcher: CoroutineContext
                get() = Dispatchers.IO
            override val mainDispatcher: CoroutineContext
                get() = Dispatchers.Main
        }
    }

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


}

interface DispatcherProvider {
    val ioDispatcher: CoroutineContext
    val mainDispatcher: CoroutineContext
}