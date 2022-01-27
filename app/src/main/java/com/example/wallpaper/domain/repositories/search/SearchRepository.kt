package com.example.wallpaper.domain.repositories.search

import com.example.wallpaper.domain.models.CuratedListResponseModel
import com.example.wallpaper.utils.DataResult

interface SearchRepository {

    suspend fun fetchImagesForSearchQuery(
        searchQuery: String,
        pageNumber: Int
    ) : DataResult<CuratedListResponseModel>
}