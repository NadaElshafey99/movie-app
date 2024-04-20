package com.example.movieapp.data.remote.remote_data_source

import com.example.movieapp.hepers.UiState

interface IRemoteDataSource {
    suspend fun fetchMovies(): UiState
}