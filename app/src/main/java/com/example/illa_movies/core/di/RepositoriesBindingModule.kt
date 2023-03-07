package com.example.illa_movies.core.di

import com.example.illa_movies.core.domain.repository.OmdbRepo
import com.example.illa_movies.core.domain.repository.OmdbRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesBindingModule {

    /**
     * Binds an [OmdbRepoImpl] instance to the [OmdbRepo] interface.
     * @param omdbRepoImpl An instance of [OmdbRepoImpl].
     * @return An instance of [OmdbRepo].
     */
    @Binds
    abstract fun bindOmdbRepo(omdbRepoImpl: OmdbRepoImpl): OmdbRepo
}