package com.example.illa_movies.core.data_source.remote

import com.example.illa_movies.BuildConfig
import com.example.illa_movies.core.data_source.remote.data.OmdbSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

//http://www.omdbapi.com/?i=tt3896198&apikey=44feeb2c
interface OmdbService {
    companion object {
        const val BASE_URL = "https://www.omdbapi.com/"
        const val OMDb_API_KEY = BuildConfig.OMDB_API_KEY
    }

    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String = OMDb_API_KEY,
        @Query("s") title: String,
        @Query("page") page: Int,
        @Query("type") type: String,
        @Query("y") year: String,
    ): OmdbSearchResult
}