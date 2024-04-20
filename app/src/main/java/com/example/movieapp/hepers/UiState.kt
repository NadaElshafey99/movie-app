package com.example.movieapp.hepers

sealed class UiState {
    data class Success<T>(val data: T?): UiState()
    data class Error<T>(val error: T?): UiState()
    object NetworkException : UiState()
    object Loading: UiState()
}