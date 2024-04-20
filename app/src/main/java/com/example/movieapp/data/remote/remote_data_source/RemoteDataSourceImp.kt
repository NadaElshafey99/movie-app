package com.example.movieapp.data.remote.remote_data_source

import com.example.movieapp.data.remote.api_service.MovieService
import com.example.movieapp.hepers.Constants
import com.example.movieapp.hepers.UiState
import com.example.movieapp.model.ErrorFetchMovieList
import com.google.gson.Gson
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(private val movieService: MovieService) :
    IRemoteDataSource {
    override suspend fun fetchMovies(): UiState {
        return try {
            val response = movieService.getMovies(
                header = Constants.HEADER,
                apiKey = Constants.API_KEY
            )
            if (response.isSuccessful) {
                UiState.Success(response.body())
            } else {
                val errorBody = Gson().fromJson(
                    response.errorBody()?.charStream(),
                    ErrorFetchMovieList::class.java
                )
                UiState.Error(errorBody)
            }
        } catch (e: IOException) {
            UiState.NetworkException
        } catch (ex: Exception) {
            UiState.Error(null)
        }
    }
}