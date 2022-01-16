package com.example.wallpaper.domain.repositories.home

import com.example.wallpaper.domain.models.CuratedListResponseModel
import com.example.wallpaper.domain.network.PexelsApiService
import com.example.wallpaper.domain.repositories.handleResponseToDataResult
import com.example.wallpaper.utils.DataResult
import com.example.wallpaper.utils.Injector
import java.lang.Exception

class HomeRepositoryImpl(
    private val api: PexelsApiService = Injector.pexelApiService
) : HomeRepository {

    override suspend fun fetchCuratedList(pageNumber: Int): DataResult<CuratedListResponseModel> {
        return try {
            api.fetchCuratedImages(pageNumber).handleResponseToDataResult()
        } catch (e: Exception) {
            DataResult.Error("Something went wrong")
        }
    }
}