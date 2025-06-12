package com.project.onycheck.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedCirclesLoader(modifier: Modifier = Modifier) {
    val gradient1 = Brush.verticalGradient(listOf(Color(0xFF93C5FD), Color(0xFF2563EB)))
    val gradient2 = Brush.verticalGradient(listOf(Color(0xFF60A5FA), Color(0xFF1D4ED8)))
    val gradient3 = Brush.verticalGradient(listOf(Color(0xFF3B82F6), Color(0xFF1E40AF)))

    val circles = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 300L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 2000
                        0.0f at 0
                        0.5f at 500
                        0.8f at 1000
                        0.2f at 1500
                        0.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }

    Canvas(modifier = modifier) {
        val radius = 5.dp.toPx()

        val c1_x = lerp(5.dp.toPx(), 49.dp.toPx(), circleValues[0])
        val c1_y = lerp(40.dp.toPx(), 20.dp.toPx(), (sin(circleValues[0] * Math.PI * 2) * 0.5 + 0.5).toFloat())

        val c2_x = lerp(5.dp.toPx(), 49.dp.toPx(), circleValues[1])
        val c2_y = lerp(20.dp.toPx(), 40.dp.toPx(), (sin(circleValues[1] * Math.PI * 2) * 0.5 + 0.5).toFloat())

        val c3_x = lerp(49.dp.toPx(), 5.dp.toPx(), circleValues[2])
        val c3_y = lerp(40.dp.toPx(), 20.dp.toPx(), (cos(circleValues[2] * Math.PI * 2) * 0.5 + 0.5).toFloat())

        drawCircle(brush = gradient1, radius = radius, center = Offset(c1_x, c1_y))
        drawCircle(brush = gradient2, radius = radius, center = Offset(c2_x, c2_y))
        drawCircle(brush = gradient3, radius = radius, center = Offset(c3_x, c3_y))
    }
}