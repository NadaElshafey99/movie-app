package com.example.movieapp.repositories.movie_repository

import com.example.movieapp.data.remote.remote_data_source.IRemoteDataSource
import com.example.movieapp.hepers.UiState
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val remoteDataSource: IRemoteDataSource) :
    IMovieRepository {
    override suspend fun fetchMovies(): UiState = remoteDataSource.fetchMovies()
    override suspend fun getDetailsForSelectedMovie(movieId: Int): UiState =
        remoteDataSource.getDetailsForSelectedMovie(movieId)

    override suspend fun searchMovies(wordSearchOn: String): UiState = remoteDataSource.searchOnMovie(wordSearchOn)
}