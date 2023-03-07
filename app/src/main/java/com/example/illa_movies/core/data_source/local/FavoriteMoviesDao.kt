package com.example.illa_movies.core.data_source.local

import androidx.room.*
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie
import kotlinx.coroutines.flow.Flow

/**
 * An interface that defines database operations for the favorite movies table.
 */
@Dao
interface FavoriteMoviesDao {

    /**
     * Inserts or replaces a movie in the favorite movies table.
     * @param movie The movie to add or replace in the table.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(movie: OmdbMovie)

    /**
     * Gets a list of all the favorite movies from the table as a [Flow] of [List]<[OmdbMovie]>.
     * @return A [Flow] that emits a [List] of [OmdbMovie] objects when the table is updated.
     */
    @Query("SELECT * FROM movies")
    fun getFavoriteMovies(): Flow<List<OmdbMovie>>

    /**
     * Gets a movie from the favorite movies table with a specific IMDB ID.
     * @param imdbID The IMDB ID of the movie to get.
     * @return The [OmdbMovie] object with the specified IMDB ID, or null if it doesn't exist in the table.
     */
    @Query("SELECT * FROM movies WHERE imdbID = :imdbID")
    suspend fun getMovieById(imdbID: String): OmdbMovie?

    /**
     * Deletes a movie from the favorite movies table.
     * @param movie The [OmdbMovie] object to delete from the table.
     */
    @Delete
    fun delete(movie: OmdbMovie)
}