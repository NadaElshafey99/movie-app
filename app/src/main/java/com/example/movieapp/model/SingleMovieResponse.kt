package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class SingleMovieResponse (
    @SerializedName("id")
    val movieId: Int,
    @SerializedName("backdrop_path")
    val moviePoster: String?,
    val genres: List<Genre>,
    @SerializedName("homepage")
    val movieUrl: String?,
    @SerializedName("original_title")
    val movieTitle: String,
    @SerializedName("overview")
    val detailedInformation: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val rating: Double?,
)

data class Genre(
    val id: Int,
    val name: String
)
