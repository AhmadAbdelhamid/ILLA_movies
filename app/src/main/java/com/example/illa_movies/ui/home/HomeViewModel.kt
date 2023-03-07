package com.example.illa_movies.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.illa_movies.core.data_source.remote.data.OmdbMovie
import com.example.illa_movies.core.domain.repository.OmdbRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val omdbRepo: OmdbRepo,
) : ViewModel() {
    private var pagingJob: Job? = null

    private val _moviesFlow = MutableStateFlow<PagingData<OmdbMovie>>(PagingData.empty())
    val moviesFlow = _moviesFlow.asStateFlow()
    init {
        getMovies("Love")
    }
    fun getMovies(movieTitle: String) {
        pagingJob?.cancel()
        pagingJob = viewModelScope.launch {

            omdbRepo.searchMovies(movieTitle).cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _moviesFlow.value = pagingData
                }
        }

    }

    override fun onCleared() {
        super.onCleared()
        pagingJob?.cancel()
    }
}