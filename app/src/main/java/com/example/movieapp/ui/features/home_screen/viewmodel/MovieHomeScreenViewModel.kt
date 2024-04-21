package com.example.movieapp.ui.features.home_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.hepers.UiState
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieListResponse
import com.example.movieapp.repositories.movie_repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieHomeScreenViewModel @Inject constructor(
    private val movieRepository: IMovieRepository
): ViewModel() {
    private val _searchResultsResponse = MutableStateFlow<UiState>(UiState.Loading)
    val searchResultsResponse
        get() = _searchResultsResponse.asStateFlow()

    private val _moviesResponse = MutableStateFlow<UiState?>(UiState.Loading)
    val moviesResponse
        get() = _moviesResponse.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText
        get() = _searchText.asStateFlow()
    private val _filteredMovies = MutableStateFlow(listOf<Movie>())
    val filteredMovies = searchText
        .debounce(1000L)
        .onEach {
            if (it.isNotEmpty()) {
                searchMovie(it)
            }
        }
        .combine(_filteredMovies) { text, movies ->
            movies.filter {
                it.doesMatchSearchQuery(text)
            }
        }
        .stateIn( //to convert the Flow into a StateFlow
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _filteredMovies.value
        )

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = movieRepository.fetchMovies()
            withContext(Dispatchers.Main) {
                _filteredMovies.value = emptyList()
                _moviesResponse.value = movies
            }
        }
    }
    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }
    private fun searchMovie(wordSearchOn: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchResultsResponse.value = movieRepository.searchMovies(wordSearchOn)
            if (_searchResultsResponse.value is UiState.Success<*>) {
                val data =
                    (_searchResultsResponse.value as UiState.Success<MovieListResponse>).data
                _filteredMovies.value = data?.movies ?: listOf()
            }
        }
    }
    fun reloadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _moviesResponse.value = UiState.Loading
            delay(2000L)
            getMovies()
        }
    }
}