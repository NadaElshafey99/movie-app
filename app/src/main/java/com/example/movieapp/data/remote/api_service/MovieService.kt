package com.example.movieapp.data.remote.api_service

import com.example.movieapp.model.MovieListResponse
import com.example.movieapp.model.SingleMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/week")
    suspend fun getMovies(
        @Header("accept") header: String,
        @Query("api_key") apiKey: String
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Header("accept") header: String,
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): Response<SingleMovieResponse>

}