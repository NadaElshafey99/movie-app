package com.example.movieapp.ui.common_composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun StarRatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 10
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStars = !(rating.rem(1).equals(0.0))
    Row (modifier = modifier,verticalAlignment = Alignment.CenterVertically){
        repeat(filledStars){
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.fill_star),
                contentDescription = "rating",
                tint= Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        if(halfStars){
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.half_star),
                contentDescription = "rating",
                tint= Color.Unspecified
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
        repeat(unfilledStars){
            Icon(
                tint= Color.Unspecified,
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.empty_star),
                contentDescription = "rating",
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}