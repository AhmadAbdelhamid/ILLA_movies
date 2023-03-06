package com.example.illa_movies.core.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.illa_movies.core.data_source.remote.OmdbService
import com.example.illa_movies.core.data_source.remote.data.OmdbMovie
import com.example.illa_movies.core.domain.OmdbPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface OmdbRepo {
    suspend fun searchMovies(movieTitle: String): Flow<PagingData<OmdbMovie>>
}


class OmdbRepoImpl @Inject constructor(
    private val omdbService: OmdbService
) : OmdbRepo {
    override suspend fun searchMovies(
        movieTitle: String,
    ): Flow<PagingData<OmdbMovie>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = {
                OmdbPagingSource(
                    omdbService = omdbService,
                    movieTitle = movieTitle,
                )
            }
        ).flow.flowOn(Dispatchers.IO)
    }
}