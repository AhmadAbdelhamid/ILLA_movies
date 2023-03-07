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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.illa_movies.ui.common_ui_component.MovieItem
import com.example.illa_movies.ui.common_ui_component.SearchInput

/**
 * Composable function for displaying the Movies Home Screen UI.
 * @param modifier Modifier for adjusting the layout of the screen.
 * @param viewModel ViewModel that provides data for the screen.
 */

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MoviesHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // Collect movies using lazy paging
    val lazyPagingMovies = viewModel.moviesFlow.collectAsLazyPagingItems()

    // Determine if loading should be displayed while movies are not empty & paging state in loading too,
    // to avoid showing two loading indicators
    val isLoadingWhileMoviesNotEmpty by remember(lazyPagingMovies.loadState.refresh) {
        derivedStateOf {
            val isLoading = lazyPagingMovies.loadState.refresh == LoadState.Loading
            isLoading && (lazyPagingMovies.itemCount > 0)
        }
    }

    // Create pull-to-refresh state for the screen
    val pullRefreshState = rememberPullRefreshState(
        isLoadingWhileMoviesNotEmpty,
        onRefresh = {
            lazyPagingMovies.refresh()
        })

    // Display the screen content inside a Box with pull-to-refresh functionality
    Box(modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Display a search input at the top of the screen
            stickyHeader {
                SearchInput { query -> viewModel.getMovies(query) }
            }

            // Display a list of movies
            items(
                items = lazyPagingMovies,
                key = { it.imdbId }
            ) { movie ->

                if (movie == null) return@items // don't show any thing if movie is null

                // Display a single movie item
                MovieItem(
                    movie = movie,
                    onClick = {
                        viewModel.onFavIconClicked(movie)
                        lazyPagingMovies.refresh()
                    },
                )
            }

            // Render the loading UI when the screen is first loaded
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

            // Render the loading UI when pagination is occurring
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

        // Render the pull-to-refresh indicator
        PullRefreshIndicator(
            lazyPagingMovies.loadState.refresh == LoadState.Loading,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colors.primaryVariant
        )
    }
}

/**
 * A Composable function that renders a loading UI with a message.
 * @param message The message to display alongside the loading UI.
 * @return A Composable that renders a loading UI.
 */
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











