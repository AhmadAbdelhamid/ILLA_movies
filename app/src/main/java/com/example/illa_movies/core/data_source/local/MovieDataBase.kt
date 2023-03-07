package com.example.illa_movies.core.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie

/**
 * An abstract class that defines the database and its entities for the app.
 */
@Database(entities = [OmdbMovie::class], version = 1)
abstract class OmdbDatabase : RoomDatabase() {

    /**
     * Gets a DAO for the favorite movies table.
     * @return An instance of [FavoriteMoviesDao] that can be used to interact with the favorite movies table.
     */
    abstract fun favoriteMoviesDao(): FavoriteMoviesDao
}


