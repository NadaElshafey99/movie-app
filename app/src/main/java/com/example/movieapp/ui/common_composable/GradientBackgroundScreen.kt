package com.example.movieapp.ui.common_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

@Composable
fun GradientBackgroundScreen() {
    val colorStops = arrayOf(
        0f to MaterialTheme.colorScheme.primary,
        0.8f to MaterialTheme.colorScheme.secondary,
        1f to MaterialTheme.colorScheme.tertiary
    )
    val brush = Brush.verticalGradient(colorStops = colorStops)
    Box(modifier = Modifier.fillMaxSize().background(brush))
}