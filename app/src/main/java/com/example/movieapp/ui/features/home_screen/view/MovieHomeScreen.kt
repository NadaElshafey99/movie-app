package com.example.movieapp.ui.features.home_screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapp.MainActivity
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
    val searchText = movieViewModel.searchText.collectAsState()
    val searchResults = movieViewModel.searchResultsResponse.collectAsState()
    val filteredMovies = movieViewModel.filteredMovies.collectAsState()

    LaunchedEffect(key1 = searchText.value.isEmpty()) {
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
            moviesList.clear()
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
    if(searchText.value.isNotEmpty()){
        when (searchResults.value) {
            is UiState.Success<*> -> {
                errorMessage = ""
                moviesList.clear()
                moviesList.addAll(filteredMovies.value)
            }
            is UiState.Error<*> -> {
                val error = (searchResults.value as UiState.Error<ErrorResponse>).error
                errorMessage = error?.errorMsg ?: stringResource(id = R.string.unknown_error)
            }
            else -> {}
        }
    }
    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingIndicator(loading = true)
        }
    }
    if(!loading && errorMessage.isNotEmpty()) {
        val context = LocalContext.current
        ErrorDialog(
            description = errorMessage,
            positiveButtonText = R.string.try_again,
            negativeButtonText = R.string.cancel,
            animatedId = R.raw.error,
            onPositiveClick = { movieViewModel.reloadMovies() },
            onNegativeClick = { (context as MainActivity).finish() })
    }
    MovieHomeScreenContent(
        movieList = moviesList,
        loading = loading,
        searchText = searchText.value,
        onSearchChanged = movieViewModel::onSearchTextChanged,
        homeNavController = navController
    )
}

@Composable
fun MovieHomeScreenContent(
    movieList: List<Movie>,
    loading: Boolean,
    searchText: String,
    onSearchChanged: (String) -> Unit,
    homeNavController: NavHostController
) {
    Column(modifier = Modifier.padding(12.dp)) {
        Box(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 18.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchText,
                onValueChange = onSearchChanged,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary.copy(0.3F),
                    focusedContainerColor = MaterialTheme.colorScheme.secondary.copy(0.3F),
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.background
                ),
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                placeholder = { Text(text = stringResource(id = R.string.search)) }
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 24.dp),
            text = stringResource(id = R.string.trending_movies),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        )
        if(!loading && movieList.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(72.dp),
                        painter = painterResource(id = R.drawable.empty_movie),
                        contentDescription = "alert",
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.there_is_no_movie),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )

                }
            }
        }
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
}