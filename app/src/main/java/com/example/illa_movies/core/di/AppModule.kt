package com.example.illa_movies.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [
    NetworkModule::class,
    RepositoriesBindingModule::class,
])
object AppModule {
}