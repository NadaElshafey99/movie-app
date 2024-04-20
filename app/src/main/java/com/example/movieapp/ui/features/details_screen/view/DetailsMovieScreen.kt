package com.example.movieapp.ui.features.details_screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.hepers.Constants
import com.example.movieapp.hepers.UiState
import com.example.movieapp.model.ErrorResponse
import com.example.movieapp.model.SingleMovieResponse
import com.example.movieapp.ui.common_composable.ErrorDialog
import com.example.movieapp.ui.common_composable.LoadingIndicator
import com.example.movieapp.ui.common_composable.StarRatingBar
import com.example.movieapp.ui.features.details_screen.viewmodel.DetailsScreenViewModel

@Composable
fun DetailsMovieScreen(navController: NavHostController, movieId: Int?) {
    var loading by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf("")
    }
    var selectedMovie: SingleMovieResponse? by remember {
        mutableStateOf(null)
    }
    val detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel()
    val detailedMovieResponse = detailsScreenViewModel.movieResponse.collectAsState()
    LaunchedEffect(key1 = movieId) {
        if (movieId != null) {
            detailsScreenViewModel.getDetailsForSelectedMovie(movieId)
        }
    }
    when (val movie = detailedMovieResponse.value) {
        is UiState.Loading -> {
            loading = true
        }

        is UiState.Success<*> -> {
            loading = false
            val movieResponse = (movie as UiState.Success<SingleMovieResponse>).data
            selectedMovie = movieResponse
        }

        is UiState.Error<*> -> {
            loading = false
            val error = (movie as UiState.Error<ErrorResponse>).error
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
            onPositiveClick = {
                if (movieId != null) {
                    detailsScreenViewModel.reloadDetailedMovie(movieId = movieId)
                }
            },
            onNegativeClick = { navController.popBackStack() })
    }
    selectedMovie?.let {
        DetailsMovieContentScreen(
            detailsNavController = navController,
            movie = it
        )
    }
}

@Composable
private fun DetailsMovieContentScreen(
    detailsNavController: NavHostController,
    movie: SingleMovieResponse
) {
    val colorStops = arrayOf(
        0f to MaterialTheme.colorScheme.primary,
        0.2f to MaterialTheme.colorScheme.secondary,
        1f to MaterialTheme.colorScheme.tertiary
    )
    val brush = Brush.verticalGradient(colorStops = colorStops)

    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            model = "${Constants.IMAGE_URL}${movie.moviePoster}",
            placeholder = painterResource(id = R.drawable.movie_loading_placeholder),
            error = painterResource(id = R.drawable.movie_unavailable_image),
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .drawBehind { drawCircle(color = Color.LightGray) },
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = { detailsNavController.popBackStack() }) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "back_arrow"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 290.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(brush)
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = movie.movieTitle, style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = movie.releaseDate, style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            )
            StarRatingBar(
                modifier = Modifier.padding(vertical = 8.dp),
                rating = movie.rating ?: 0.0
            )

            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = stringResource(id = R.string.description),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = movie.detailedInformation ?: "",
                lineHeight = 24.sp,
                style = TextStyle(
                    color = Color.White
                )
            )
        }
    }
}