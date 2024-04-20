package com.example.movieapp.ui.features.home_screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.common_composable.MovieItem
import com.example.movieapp.ui.navigation.Screens

@Composable
fun MovieHomeScreen(navController: NavHostController) {
    MovieHomeScreenContent(
        movieList = listOf("Movie", "Movie", "Movie"),
        homeNavController = navController
    )
}

@Composable
fun MovieHomeScreenContent(
    movieList: List<String>,
    homeNavController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyGridState()
    ) {
        items(movieList.size) { index ->
            MovieItem(
                modifier = Modifier.clickable {
                    homeNavController.navigate("${Screens.DetailsScreen.route}/21")
                },
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
    MovieHomeScreen(navController = rememberNavController())
}