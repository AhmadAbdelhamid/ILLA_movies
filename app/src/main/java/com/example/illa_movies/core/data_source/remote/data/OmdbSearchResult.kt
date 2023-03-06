package com.example.illa_movies.core.data_source.remote.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class OmdbSearchResult(
    @SerializedName("Search")
    val search: List<OmdbMovie>,
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("Response")
    val response: String, //True, False
) : Parcelable

@Parcelize
data class OmdbMovie(
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Poster")
    val posterUrl: String
) : Parcelable

@Parcelize
data class ErrorResponse(
    @SerializedName("Error")
    val error: String,
    @SerializedName("Response")
    val response: String
) : Parcelable