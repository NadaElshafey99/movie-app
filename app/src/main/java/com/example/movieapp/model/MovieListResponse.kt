package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse (
    @SerializedName("results")
    val movies: List<Movie>?,
)

data class Movie(
    @SerializedName("id")
    val id: Long,
    @SerializedName("poster_path")
    val posterImg: String?,
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String,
){
    fun doesMatchSearchQuery(word: String): Boolean{
        val matchingCombination = listOf(
            title
        )
        return matchingCombination.any {
            it.contains(word, true)
        }
    }
}