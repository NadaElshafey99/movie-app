package com.example.movieapp.repositories.movie_repository

import com.example.movieapp.hepers.UiState

interface IMovieRepository {
    suspend fun fetchMovies(): UiState
}