package com.example.movieapp.ui.features.details_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.hepers.UiState
import com.example.movieapp.model.ErrorResponse
import com.example.movieapp.repositories.movie_repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val movieRepository: IMovieRepository
) : ViewModel() {
    private val _movieResponse = MutableStateFlow<UiState?>(UiState.Loading)
    val movieResponse
        get() = _movieResponse.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val isNetworkIssue = when (exception) {
            is IOException -> true
            is HttpException -> true
            else -> false
        }
        if (isNetworkIssue) {
            _movieResponse.value = UiState.NetworkException
        } else {
            _movieResponse.value = UiState.Error(ErrorResponse(null))
        }
    }
    fun getDetailsForSelectedMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val movie = movieRepository.getDetailsForSelectedMovie(movieId)
            withContext(Dispatchers.Main) {
                _movieResponse.value = movie
            }
        }
    }

    fun reloadDetailedMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieResponse.value = UiState.Loading
            delay(2000L)
            getDetailsForSelectedMovie(movieId = movieId)
        }
    }
}