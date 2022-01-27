package com.example.wallpaper.domain.repositories.search

import com.example.wallpaper.domain.models.CuratedListResponseModel
import com.example.wallpaper.domain.network.PexelsApiService
import com.example.wallpaper.domain.repositories.handleResponseToDataResult
import com.example.wallpaper.utils.DataResult
import com.example.wallpaper.utils.Injector

class SearchRepositoryImpl(
    private val api: PexelsApiService = Injector.pexelApiService
) : SearchRepository {

    override suspend fun fetchImagesForSearchQuery(
        searchQuery: String,
        pageNumber: Int,
    ): DataResult<CuratedListResponseModel> {
        return when(val dataResult = api.searchImageForQuery(searchQuery, pageNumber).handleResponseToDataResult()) {
            is DataResult.Success -> {
                dataResult.apply {
                    data.correspondingQueryForThisResult = searchQuery
                }
            }
            else -> dataResult
        }
    }
}