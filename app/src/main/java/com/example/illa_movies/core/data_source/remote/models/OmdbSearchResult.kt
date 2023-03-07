package com.example.illa_movies.core.data_source.remote.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * A data class that represents the result of an OMDB search.
 * @param movieList A list of [OmdbMovie] objects that match the search query.
 * @param totalResults The total number of search results available.
 * @param response A string that indicates whether the search was successful or not ("True" or "False").
 * @param error An optional error message if the search was not successful.
 */
@Parcelize
data class OmdbSearchResult(

    @SerializedName("Search")
    val movieList: List<OmdbMovie>? = null,

    @SerializedName("totalResults")
    val totalResults: Int = 0,

    @SerializedName("Response")
    val response: String,

    @SerializedName("Error")
    val error: String? = null,

    ) : Parcelable {

    val isSuccessful: Boolean get() = response.equals("True", ignoreCase = true)

}

/**
 * A data class that represents a movie retrieved from the OMDB API.
 * @param imdbId The IMDB ID of the movie.
 * @param title The title of the movie.
 * @param year The release year of the movie.
 * @param posterUrl The URL of the movie poster image.
 * @param isLiked A flag indicating whether the user has liked this movie or not.
 */
@Entity(tableName = "movies")
@Parcelize
data class OmdbMovie(

    @SerializedName("imdbID")
    @PrimaryKey
    val imdbId: String = "",

    @SerializedName("Title")
    @ColumnInfo(name = "title")
    val title: String = "",

    @SerializedName("Year")
    val year: String = "",

    @SerializedName("Poster")
    val posterUrl: String = "",

    @ColumnInfo(name = "liked")
    val isLiked: Boolean = false,

    ) : Parcelable