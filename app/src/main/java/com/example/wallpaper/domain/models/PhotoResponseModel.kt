package com.example.wallpaper.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CuratedListResponseModel(
    val page: Int = 1,
    @SerializedName("per_page") val perPage: Int = 0,
    val photos: List<PhotoResponseModel> = listOf(),
    @SerializedName("total_results") val totalResults: Long = 0L,
    @SerializedName("next_page") val nextPage: String = ""
) {
    var correspondingQueryForThisResult: String = ""
}

@Parcelize
data class PhotoResponseModel (
    val id: Long? = null,
    val width: Long = 0L,
    val height: Long = 0L,
    val url: String = "",
    val photographer: String = "",
    @SerializedName("photographer_url") val photographerUrl: String = "",
    @SerializedName("photographer_id") val photographerId: Long? = null,
    @SerializedName("avg_color") val avgColor: String = "#ffffff",
    @SerializedName("src") val photoSrc: PhotoSourceModel = PhotoSourceModel(),
    @SerializedName("alt") val description: String = ""
) : Parcelable

@Parcelize
data class PhotoSourceModel(
    val original: String = "",
    val large: String = "",
    val medium: String = "",
    val small: String = "",
    val portrait: String = "https://images.pexels.com/photos/10234219/pexels-photo-10234219.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800"
) : Parcelable
