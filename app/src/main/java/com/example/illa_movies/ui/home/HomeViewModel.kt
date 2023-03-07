package com.example.illa_movies.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie
import com.example.illa_movies.core.domain.repository.OmdbRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HiltViewModel annotation is used to inject dependencies into ViewModel classes
 * that are created by ViewModelFactory.
 * @param omdbRepo the repository responsible for handling the data operations
 * @property pagingJob a job that is responsible for loading paged data
 * @property _moviesFlow a mutable state flow of [PagingData] objects containing [OmdbMovie]s
 * @property moviesFlow an immutable state flow of [_moviesFlow]
 * @constructor Creates a new instance of HomeViewModel class with dependencies injected
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val omdbRepo: OmdbRepo,
) : ViewModel() {
    private var pagingJob: Job? = null

    private val _moviesFlow = MutableStateFlow<PagingData<OmdbMovie>>(PagingData.empty())
    val moviesFlow = _moviesFlow.asStateFlow()

    /**
     * Initializes the ViewModel by getting movies that match the title "fast".
     */
    init {
        getMovies("fast")
    }

    /**
     * Gets the movies that match the given [movieTitle] from the repository and updates the [_moviesFlow].
     * If the [movieTitle] is empty, this method returns immediately "do nothing".
     * This method also cancels any existing [pagingJob] to avoid multiple requests.
     */
    fun getMovies(movieTitle: String) {
        if (movieTitle.isEmpty()) return //do nothing
        pagingJob?.cancel()
        pagingJob = viewModelScope.launch {
            omdbRepo.searchMovies(movieTitle).cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _moviesFlow.value = pagingData
                }
        }
    }

    /**
     * Handles the click event on the favorite icon of a [movie].
     * If the [movie] is already in the favorites, it will be removed from the favorites.
     * If it is not in the favorites, it will be added to the favorites.
     * @param movie the [OmdbMovie] object that was clicked
     */

    fun onFavIconClicked(movie: OmdbMovie) {
        viewModelScope.launch {
            if (movie.isLiked) {
                omdbRepo.deleteFromFav(movie)
            } else {
                omdbRepo.addMovieToFav(movie)
            }
        }
    }

    /**
     * Cancels any ongoing [pagingJob] when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        pagingJob?.cancel()
    }
}