package com.example.illa_movies.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.illa_movies.R
import com.example.illa_movies.core.data_source.remote.data.OmdbMovie

@Composable
fun MoviesHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val movies = viewModel.moviesFlow.collectAsLazyPagingItems()


    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
    ) {

        items(
            items = movies,
            key = { it.imdbId }
        ) { movie ->

            if (movie == null) return@items

            MovieItem(
                movie = movie,
                onClick = {},
            )
        }

        //FIRST LOAD
        when (val state = movies.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    state.error.localizedMessage?.let {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = it
                        )
                    }
                }
            }
            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )
                    }
                }
            }
            else -> {}
        }

        // Pagination
        when (val state = movies.loadState.append) {
            is LoadState.Error -> {
                item {
                    state.error.localizedMessage?.let {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = it
                        )
                    }
                }
            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                        Text(text = "Pagination Loading")
                    }
                }
            }
            else -> {}
        }

    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: OmdbMovie,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        border = BorderStroke(0.5.dp, Color.Gray.copy(alpha = 0.4f)),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MoviePoster(imagePath = movie.posterUrl)
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = "Title: ${movie.title}"
                )
                Text(
                    text = "ImdbId: ${movie.imdbId}"
                )
                Text(
                    text = "Year: ${movie.year}"
                )
            }
            IconButton(
                modifier = Modifier,
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    tint = Color.Red,
                    contentDescription = "favorite",
                )
            }
        }
    }
}

@Composable
fun MoviePoster(
    imagePath: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(0.5.dp, Color.Gray),
        modifier = modifier
            .height(100.dp)
            .width(70.dp),
        elevation = 3.dp
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "movie poster",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}