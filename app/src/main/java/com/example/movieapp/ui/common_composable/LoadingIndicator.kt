package com.example.movieapp.ui.common_composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(loading: Boolean) {
    if (!loading) return
    CircularProgressIndicator(
        modifier = Modifier.size(64.dp),
        color = Color.White,
    )
}