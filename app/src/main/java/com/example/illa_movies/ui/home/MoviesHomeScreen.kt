package com.example.illa_movies.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.illa_movies.ui.ui_component.MovieItem
import com.example.illa_movies.ui.ui_component.SearchInput

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val lazyPagingMovies = viewModel.moviesFlow.collectAsLazyPagingItems()

    val isLoadingWhileMoviesNotEmpty by remember(lazyPagingMovies.loadState.refresh) {
        derivedStateOf {
            val isLoading = lazyPagingMovies.loadState.refresh == LoadState.Loading
            isLoading && (lazyPagingMovies.itemCount > 0)
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(
            isLoadingWhileMoviesNotEmpty,
            onRefresh = {
                lazyPagingMovies.refresh()
            })

    Box(modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            stickyHeader {
                SearchInput { query -> viewModel.getMovies(query) }
            }

            items(
                items = lazyPagingMovies,
                key = { it.imdbId }
            ) { movie ->
                if (movie == null) return@items

                MovieItem(
                    movie = movie,
                    onClick = {
                        viewModel.onFavIconClicked(movie)
                        lazyPagingMovies.refresh()
                    },
                )
            }

            //FIRST LOAD
            when (val state = lazyPagingMovies.loadState.refresh) {
                is LoadState.Error -> {
                    item {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = state.error.localizedMessage ?: "error"
                        )
                    }
                }
                is LoadState.Loading -> { // Loading UI
                    item {
                        Loading("Refresh Loading")
                    }
                }
                else -> {}
            }

            // Pagination
            when (val state = lazyPagingMovies.loadState.append) {
                is LoadState.Error -> {
                    item {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = state.error.localizedMessage ?: "error"
                        )
                    }
                }
                is LoadState.Loading -> { // Pagination Loading UI
                    item {
                        Loading("Loading...")
                    }
                }
                else -> {}
            }

        }
        PullRefreshIndicator(
            lazyPagingMovies.loadState.refresh == LoadState.Loading,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
fun Loading(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
        Text(text = message)
    }
}











