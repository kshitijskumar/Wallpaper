package com.example.wallpaper.domain.network

import com.example.wallpaper.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", BuildConfig.PEXEL_AUTH_KEY)
            .build()

        return chain.proceed(newRequest)
    }

}