package com.example.movieapp.ui.common_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.R

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    subtitle: String
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(240.dp)
            .clip(RoundedCornerShape(8))
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageUrl,
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.poster),
            error = painterResource(id = R.drawable.error)
        )
        Box(
            modifier = Modifier
                .graphicsLayer(alpha = 0.9f)
                .background(Color.Gray)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = subtitle,
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(imageUrl = "R.drawable.poster", title = "Movie  Title", subtitle = "Description")
}