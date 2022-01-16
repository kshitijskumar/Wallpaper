package com.example.wallpaper.domain.repositories

import com.example.wallpaper.utils.DataResult
import retrofit2.Response

fun <T>Response<T>.handleResponseToDataResult() : DataResult<T> {
    return if (this.isSuccessful) {
        if (body() != null) {
            DataResult.Success(body()!!)
        } else {
            DataResult.Error("No response from our end")
        }
    } else {
        DataResult.Error("Something went wrong on our end")
    }
}