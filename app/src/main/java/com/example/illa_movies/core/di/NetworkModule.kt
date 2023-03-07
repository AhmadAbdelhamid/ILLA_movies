package com.example.illa_movies.core.di

import com.example.illa_movies.BuildConfig
import com.example.illa_movies.core.data_source.remote.OmdbService
import com.example.illa_movies.core.data_source.remote.OmdbService.Companion.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a [HttpLoggingInterceptor] with the appropriate logging level based on whether the app is
     * running in debug mode or not. The interceptor helps to log requests and responses sent by the app.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    /**
     * Provides an [OkHttpClient] instance that has an [HttpLoggingInterceptor] added to it.
     * The client is used by the app to send HTTP requests to the server.
     * @param loggingInterceptor The logging interceptor used to log requests and responses.
     * @return An instance of [OkHttpClient].
     */
    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .callTimeout(60, TimeUnit.SECONDS)
        .build()

    /**
     * Provides a [Retrofit] instance configured with a base URL and a Gson converter factory.
     * The Retrofit instance is used by the app to make HTTP requests to the server.
     * @return An instance of [Retrofit].
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     * Provides an implementation of [OmdbService] that uses the [Retrofit] instance provided by [provideRetrofit]
     * to make HTTP requests to the server.
     * @param retrofit The Retrofit instance used to make HTTP requests.
     * @return An implementation of [OmdbService].
     */
    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): OmdbService =
        retrofit.create(OmdbService::class.java)

}