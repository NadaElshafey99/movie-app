package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_message")
    val errorMsg: String?
)
