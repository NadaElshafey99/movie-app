package com.example.movieapp.ui.common_composable

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ErrorDialog(
    description: String,
    @StringRes negativeButtonText: Int,
    @StringRes positiveButtonText: Int,
    @RawRes animatedId: Int,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit
)
{
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(animatedId)
    )
    Dialog(
        onDismissRequest = onNegativeClick,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 30.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onPositiveClick,
                        modifier = Modifier
                            .width(120.dp)
                            .height(36.dp)
                            .clip(
                                RoundedCornerShape(5.dp)
                            ),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(
                            text = stringResource(id = positiveButtonText),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = onNegativeClick,
                        modifier = Modifier
                            .width(120.dp)
                            .height(36.dp)
                            .clip(
                                RoundedCornerShape(5.dp)
                            ),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(
                            text = stringResource(id = negativeButtonText),
                            color = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            }
            LottieAnimation(
                composition = composition, modifier = Modifier
                    .size(120.dp)
                    .align(
                        Alignment.TopCenter
                    )
            )
        }
    }
}