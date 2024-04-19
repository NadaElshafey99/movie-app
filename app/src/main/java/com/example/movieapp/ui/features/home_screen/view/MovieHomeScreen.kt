package com.example.movieapp.ui.features.home_screen.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.common_component.MovieItem

@Composable
fun MovieHomeScreen() {
    MovieHomeScreenContent(movieList = listOf("Movie", "Movie", "Movie"))
}

@Composable
fun MovieHomeScreenContent(
    movieList: List<String>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyGridState()
    ) {
        items(movieList.size) { index ->
            MovieItem(
                imageUrl = movieList[index],
                title = movieList[index],
                subtitle = movieList[index]
            )
        }
    }
}
@Preview
@Composable
fun MovieHomeScreenPreview() {
    MovieHomeScreen()
}