package com.example.movieapp.ui.features.details_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.hepers.UiState
import com.example.movieapp.repositories.movie_repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val movieRepository: IMovieRepository
) : ViewModel() {
    private val _movieResponse = MutableStateFlow<UiState?>(UiState.Loading)
    val movieResponse
        get() = _movieResponse.asStateFlow()

    fun getDetailsForSelectedMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = movieRepository.getDetailsForSelectedMovie(movieId)
            withContext(Dispatchers.Main){
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