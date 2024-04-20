package com.example.movieapp.ui.features.details_screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.R
import com.example.movieapp.ui.common_composable.ActorItem
import com.example.movieapp.ui.common_composable.StarRatingBar

@Composable
fun DetailsMovieScreen() {
}

@Composable
fun DetailsMovieContentScreen(
    movie: String
) {
    Box {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            model = movie,
            placeholder = painterResource(id = R.drawable.poster),
            error = painterResource(id = R.drawable.error),
            contentDescription = "movie poster",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .drawBehind { drawCircle(color = Color.LightGray) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = "back_arrow"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 290.dp)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(Color.Gray)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Movie name", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Date - time", style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            )
            StarRatingBar(modifier = Modifier.padding(vertical = 8.dp), rating = 3.5)
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Description...",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.White
                )
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Actors",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.White
                )
            )
            LazyRow {
                items(count = 5) {
                    ActorItem("Nada", "Nada")
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsMovieScreenPreview() {
    DetailsMovieContentScreen(movie = "")
}