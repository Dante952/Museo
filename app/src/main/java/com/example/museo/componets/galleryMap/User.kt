package com.example.museo.componets.galleryMap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

@Composable
fun User(
    modifier: Modifier = Modifier,
    positionX: Double,
    positionY: Double,
    ) {
    Canvas(
    modifier = modifier
    .size(50.dp)
    .offset(positionX.dp, positionY.dp)
    .border(1.dp, Color.Black)
    .pointerInput(Unit) {
        detectTapGestures {
//                    updatePosition()
        }
    },
    ) {
        drawCircle(Color.Magenta)
    }
}