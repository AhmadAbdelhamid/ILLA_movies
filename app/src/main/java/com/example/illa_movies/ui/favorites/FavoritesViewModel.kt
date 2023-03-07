package com.example.illa_movies.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie
import com.example.illa_movies.core.domain.repository.OmdbRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HiltViewModel annotation is used to inject dependencies into ViewModel classes
 * that are created by ViewModelFactory.
 * @param omdbRepo the repository responsible for handling the data operations
 * @property _moviesFlow a mutable state flow of a list of [OmdbMovie] objects
 * @property moviesFlow an immutable state flow of [_moviesFlow]
 * @constructor Creates a new instance of FavoritesViewModel class with dependencies injected
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val omdbRepo: OmdbRepo,
) : ViewModel() {

    private val _moviesFlow = MutableStateFlow<List<OmdbMovie>>(emptyList())
    val moviesFlow = _moviesFlow.asStateFlow()

    /**
     * Initializes the ViewModel by getting the favorite movies from the repository.
     */
    init {
        getFavoriteMovies()
    }

    /**
     * Gets the favorite movies from the repository and updates the [_moviesFlow].
     */
    private fun getFavoriteMovies() {
        viewModelScope.launch {
            omdbRepo.getFavoriteMovies().collectLatest { movies ->
                _moviesFlow.value = movies
            }
        }
    }
}