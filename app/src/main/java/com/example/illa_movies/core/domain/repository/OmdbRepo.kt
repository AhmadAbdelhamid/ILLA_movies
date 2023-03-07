package com.example.illa_movies.core.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.illa_movies.core.data_source.local.FavoriteMoviesDao
import com.example.illa_movies.core.data_source.remote.OmdbService
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie
import com.example.illa_movies.core.domain.OmdbPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


/*
    The OmdbRepo interface and OmdbRepoImpl class together make up the data layer of the app.
    The interface defines the API that the repository should follow,
    and the implementation class contains the actual implementation of those methods.

    Note: Interface and its implementation should be separated from each other but we keep both of them here for simplicity.
*/


interface OmdbRepo {

    /**
     * Search for movies by title using the Omdb API and returns a flow of [PagingData] of [OmdbMovie].
     * @param movieTitle the title of the movie to search for.
     * @return a [Flow] of [PagingData] of [OmdbMovie].
     */
    suspend fun searchMovies(movieTitle: String): Flow<PagingData<OmdbMovie>>

    /**
     * Adds a [OmdbMovie] to the favorites list.
     * @param movie the [OmdbMovie] to add.
     */
    suspend fun addMovieToFav(movie: OmdbMovie)

    /**
     * Deletes a [OmdbMovie] from the favorites list.
     * @param movie the [OmdbMovie] to delete.
     */
    suspend fun deleteFromFav(movie: OmdbMovie)

    /**
     * Gets a [Flow] of all favorite [OmdbMovie]s.
     * @return a [Flow] of all favorite [OmdbMovie]s.
     */
    suspend fun getFavoriteMovies(): Flow<List<OmdbMovie>>
}

/**
 * Implementation of [OmdbRepo] that communicates with the Omdb API and a local database.
 * @param omdbService the service used to communicate with the Omdb API.
 * @param movieDao the data access object used to access the local database.
 */
@Singleton
class OmdbRepoImpl @Inject constructor(
    private val omdbService: OmdbService,
    private val movieDao: FavoriteMoviesDao,
) : OmdbRepo {

    override suspend fun searchMovies(
        movieTitle: String,
    ): Flow<PagingData<OmdbMovie>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1,
            ),
            pagingSourceFactory = {
                OmdbPagingSource(
                    omdbService = omdbService,
                    movieTitle = movieTitle,
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { omdbMovie ->
                val movieById = movieDao.getMovieById(omdbMovie.imdbId)
                val isLiked = movieById?.isLiked ?: false
                omdbMovie.copy(isLiked = isLiked)
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addMovieToFav(movie: OmdbMovie) = withContext(Dispatchers.IO) {
        movieDao.addToFavorites(movie.copy(isLiked = true))
    }

    override suspend fun deleteFromFav(movie: OmdbMovie) = withContext(Dispatchers.IO) {
        movieDao.delete(movie)
    }

    override suspend fun getFavoriteMovies(): Flow<List<OmdbMovie>> = withContext(Dispatchers.IO) {
        movieDao.getFavoriteMovies()
    }

}