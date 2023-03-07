package com.example.illa_movies.core.data_source.remote

import com.example.illa_movies.BuildConfig
import com.example.illa_movies.core.data_source.remote.models.OmdbSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface for making requests to the OMDB API.
 */
interface OmdbService {
    companion object {
        const val BASE_URL = "https://www.omdbapi.com/"
        const val OMDb_API_KEY = BuildConfig.OMDB_API_KEY
    }

    /**
     * Sends a search request to the OMDB API and returns the result.
     * @param apiKey The OMDB API key to use for the request.
     * @param title The title of the movie to search for.
     * @param type The type of movie to search for (e.g., "movie", "series", "episode"). "movie" by default
     * @param year The release year of the movie to search for.
     * @return An [OmdbSearchResult] object containing the search results.
     * @param page The page number of the search results to retrieve.
     */
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String = OMDb_API_KEY,
        @Query("s") title: String,
        @Query("type") type: String = "movie",
        @Query("y") year: String = "",
        @Query("page") page: Int,
    ): OmdbSearchResult
}