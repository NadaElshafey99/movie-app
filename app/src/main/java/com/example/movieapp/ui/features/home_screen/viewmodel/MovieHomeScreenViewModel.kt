package com.example.movieapp.ui.features.home_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.hepers.UiState
import com.example.movieapp.repositories.movie_repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieHomeScreenViewModel @Inject constructor(
    private val movieRepository: IMovieRepository
): ViewModel() {

    private val _moviesResponse = MutableStateFlow<UiState?>(UiState.Loading)
    val moviesResponse
        get() = _moviesResponse.asStateFlow()
    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = movieRepository.fetchMovies()
            withContext(Dispatchers.Main) {
                _moviesResponse.value = movies
            }
        }
    }
}