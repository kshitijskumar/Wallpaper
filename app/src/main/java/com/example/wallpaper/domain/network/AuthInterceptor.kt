package com.example.wallpaper.domain.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "563492ad6f917000010000011b875f8e55494287ba839f51ab6d2e50")
            .build()

        return chain.proceed(newRequest)
    }

}