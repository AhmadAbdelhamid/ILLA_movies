package com.example.illa_movies.core.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.illa_movies.core.data_source.remote.OmdbService
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie

/**
 * This [PagingSource] is responsible for loading paginated data from the [omdbService] based on the [movieTitle].
 * @param omdbService an instance of the [OmdbService] interface used for fetching paginated data.
 * @param movieTitle a query parameter for searching a movie by its title.
 */


private const val STARTING_PAGE_INDEX = 1

class OmdbPagingSource(
    private val omdbService: OmdbService,
    private val movieTitle: String,
) : PagingSource<Int, OmdbMovie>() {

    /**
     * This method calculates the key for loading the next or the previous page.
     * @param state represents the current state of the [PagingState].
     * @return the key for loading the next or the previous page.
     */
    override fun getRefreshKey(state: PagingState<Int, OmdbMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * This method loads a chunk of data from the backend service with the help of the [omdbService].
     * @param params contains details about the load request, such as the number of items to be loaded.
     * @return A [LoadResult] object with the loaded data.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OmdbMovie> {
        return try {

            //The params parameter contains information about the page to load, including the page number.
            // The page variable is set to the page number if it exists,
            // or to the STARTING_PAGE_INDEX constant if it doesn't.
            val page = params.key ?: STARTING_PAGE_INDEX

            val result = omdbService.searchMovies(
                title = movieTitle,
                page = page,
            )

            //This line checks if the API call was successful.
            // If it was not, then an exception is thrown with the error message.
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