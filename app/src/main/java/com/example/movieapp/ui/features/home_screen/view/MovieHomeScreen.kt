package com.example.movieapp.ui.features.home_screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapp.R
import com.example.movieapp.hepers.Constants
import com.example.movieapp.hepers.UiState
import com.example.movieapp.model.ErrorResponse
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieListResponse
import com.example.movieapp.ui.common_composable.ErrorDialog
import com.example.movieapp.ui.common_composable.LoadingIndicator
import com.example.movieapp.ui.common_composable.MovieItem
import com.example.movieapp.ui.features.home_screen.viewmodel.MovieHomeScreenViewModel
import com.example.movieapp.ui.navigation.Screens

@Composable
fun MovieHomeScreen(navController: NavHostController) {
    val moviesList = remember {
        mutableStateListOf<Movie>()
    }
    var errorMessage by remember {
        mutableStateOf("")
    }
    var loading by remember {
        mutableStateOf(false)
    }
    val movieViewModel: MovieHomeScreenViewModel = hiltViewModel()
    val moviesResponse = movieViewModel.moviesResponse.collectAsState()
    LaunchedEffect(key1 = Unit) {
        movieViewModel.getMovies()
    }
    when (val movies = moviesResponse.value) {
        is UiState.Loading -> {
            loading = true
            errorMessage = ""
        }

        is UiState.Success<*> -> {
            loading = false
            errorMessage = ""
            val movieResponse = (movies as UiState.Success<MovieListResponse>).data
            moviesList.addAll(movieResponse?.movies ?: emptyList())
        }

        is UiState.Error<*> -> {
            loading = false
            val error = (movies as UiState.Error<ErrorResponse>).error
            errorMessage = error?.errorMsg ?: stringResource(id = R.string.unknown_error)
        }

        is UiState.NetworkException -> {
            loading = false
            errorMessage = stringResource(id = R.string.no_network)
        }

        else -> {}
    }
    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingIndicator(loading = true)
        }
    }
    if(!loading && errorMessage.isNotEmpty()){
        ErrorDialog(
            description = errorMessage,
            positiveButtonText = R.string.try_again,
            negativeButtonText = R.string.cancel,
            animatedId = R.raw.error,
            onPositiveClick = { movieViewModel.reloadMovies() },
            onNegativeClick = { navController.popBackStack() })
    }
    MovieHomeScreenContent(
        movieList = moviesList,
        homeNavController = navController
    )
}

@Composable
fun MovieHomeScreenContent(
    movieList: List<Movie>,
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
                    homeNavController.navigate("${Screens.DetailsScreen.route}/${movieList[index].id}")
                },
                imageUrl = "${Constants.IMAGE_URL}${movieList[index].posterImg}",
                title = movieList[index].title,
                subtitle = movieList[index].releaseDate
            )
        }
    }
}