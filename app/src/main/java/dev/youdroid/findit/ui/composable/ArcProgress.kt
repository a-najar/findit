package dev.youdroid.findit.ui.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke


import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircularProgressBar(
    percentage: Float,
    animationDuration: Int = 1000,
    child: @Composable () -> Unit
) {

    var animFinished by remember {
        mutableStateOf(false)
    }

    val currentProgress = animateFloatAsState(
        targetValue = if (animFinished) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        animFinished = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
    ) {
        Canvas(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        ) {
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1A3194),
                        Color(0xFF1D41C5),
                        Color(0xFF7F98FF)
                    )
                ),
                startAngle = -90f,
                sweepAngle = 360 * currentProgress.value,
                useCenter = false,
                style = Stroke(
                    7.dp.toPx(),
                    cap = StrokeCap.Round,
                ),
            )
        }
        child()
    }
}