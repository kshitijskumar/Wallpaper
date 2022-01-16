package com.example.wallpaper.domain.network

import com.example.wallpaper.domain.models.CuratedListResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {

    companion object {
        private const val COMMON_PATH = "v1/"
    }

    @GET("${COMMON_PATH}curated/?per_page=20")
    suspend fun fetchCuratedImages(
        @Query("page") page: Int = 1
    ) : Response<CuratedListResponseModel>

}