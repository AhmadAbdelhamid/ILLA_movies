package com.example.illa_movies.core.domain.repository

import com.example.illa_movies.core.data_source.remote.OmdbService
import javax.inject.Inject


interface OmdbRepo{
    suspend fun searchMovies()
}


class OmdbRepoImpl @Inject constructor(
    private val omdbService: OmdbService
):OmdbRepo {
    override suspend fun searchMovies() {
        TODO("Not yet implemented")
    }
}