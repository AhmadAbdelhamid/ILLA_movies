package com.example.illa_movies.core.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.illa_movies.core.data_source.remote.OmdbService
import com.example.illa_movies.core.data_source.remote.data.OmdbMovie

const val STARTING_PAGE_INDEX = 1

class OmdbPagingSource(
    private val omdbService: OmdbService,
    private val movieTitle: String,
) : PagingSource<Int, OmdbMovie>() {

    override fun getRefreshKey(state: PagingState<Int, OmdbMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OmdbMovie> {
        return try {

            val page = params.key ?: STARTING_PAGE_INDEX

            val result = omdbService.searchMovies(
                title = movieTitle,
                page = page,
            )

            if (!result.isSuccessful)
                return LoadResult.Error(Exception(result.error))

            val omdbMovies = result.movieList

            val endOfPaginationReached = omdbMovies.isNullOrEmpty()

            if (endOfPaginationReached)
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )

            LoadResult.Page(
                data = omdbMovies ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(Exception("Sorry,something went bad!"))
        }
    }
}