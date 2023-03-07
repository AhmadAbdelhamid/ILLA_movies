package com.example.illa_movies.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie
import com.example.illa_movies.ui.ui_component.MovieItem


@Composable
fun FavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val movies by viewModel.moviesFlow.collectAsState()
    Box(modifier = modifier) {

        if (movies.isEmpty())
            EmptyText(
                modifier = Modifier.align(Alignment.Center),
            )
        else
            FavoriteMoviesComponent(movies)
    }
}

@Composable
fun EmptyText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "You don't have favorite movies",
    )
}

@Composable
private fun FavoriteMoviesComponent(movies: List<OmdbMovie>) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = movies,
        ) { movie ->
            MovieItem(
                movie = movie,
                onClick = {},
            )
        }
    }
}