package com.example.illa_movies.core.data_source.remote.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class OmdbSearchResult(
    @SerializedName("Search")
    val movieList: List<OmdbMovie>? = null,
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("Response")
    val response: String, //True, False
    @SerializedName("Error")
    val error: String? = null, //True, False
) : Parcelable{
    val isSuccessful: Boolean get() = response.equals("True",ignoreCase = true)
}

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