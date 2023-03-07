package com.example.illa_movies.ui.ui_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.illa_movies.R
import com.example.illa_movies.core.data_source.remote.models.OmdbMovie

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: OmdbMovie,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier
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
                    imageVector = if (movie.isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
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

@Preview
@Composable
fun MovieItemPreview() {
    Column {
        MovieItem(
            movie = OmdbMovie(imdbId = "123", title = "title", year = "2023"),
            onClick = {}
        )
        MovieItem(
            movie = OmdbMovie(imdbId = "123", title = "title", year = "2023", isLiked = true),
            onClick = {}
        )
    }
}