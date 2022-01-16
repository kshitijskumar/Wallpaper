package com.example.wallpaper.domain.repositories.home

import com.example.wallpaper.domain.models.CuratedListResponseModel
import com.example.wallpaper.utils.DataResult

interface HomeRepository {

    suspend fun fetchCuratedList(pageNumber: Int) : DataResult<CuratedListResponseModel>

}