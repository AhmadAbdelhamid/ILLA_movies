package com.example.illa_movies.core.di

import android.content.Context
import androidx.room.Room
import com.example.illa_movies.core.data_source.local.FavoriteMoviesDao
import com.example.illa_movies.core.data_source.local.OmdbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This is the main module of the application, it provides the [OmdbDatabase] and [FavoriteMoviesDao] for the database operations.
 * It also includes [NetworkModule] and [RepositoriesBindingModule] as its sub-modules.
 */
@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        NetworkModule::class,
        RepositoriesBindingModule::class,
    ]
)
object AppModule {

    /**
     * Provides an instance of [OmdbDatabase] using [Room] database builder.
     * @param appContext the context of the application
     * @return an instance of [OmdbDatabase]
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): OmdbDatabase =
        Room.databaseBuilder(appContext, OmdbDatabase::class.java, "Fav_Movies").build()

    /**
     * Provides an instance of [FavoriteMoviesDao] for performing database operations.
     * @param movieDatabase the instance of [OmdbDatabase]
     * @return an instance of [FavoriteMoviesDao]
     */
    @Singleton
    @Provides
    fun provideMovieDetailDao(movieDatabase: OmdbDatabase): FavoriteMoviesDao =
        movieDatabase.favoriteMoviesDao()
}