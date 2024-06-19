package com.example.museo.componets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun RoomScreen() {
    // Estado para almacenar la posición del círculo
    var circlePosition by remember { mutableStateOf(Offset(50f, 50f)) }

    // Estado para almacenar el tamaño del rectángulo negro
    var rectSize by remember { mutableStateOf(Size.Zero) }

    // Para obtener la densidad del dispositivo
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp) // Ajusta el padding según sea necesario
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val paddingPx = with(density) { 16.dp.toPx() }
                    val rectWidth = size.width - paddingPx * 2
                    val rectHeight = size.height - paddingPx * 2
                    rectSize = Size(rectWidth, rectHeight)

                    drawPath(
                        path = createRectanglePath(rectWidth, rectHeight, paddingPx),
                        color = Color.Black,
                        style = Stroke(width = with(density) { 5.dp.toPx() })
                    )

                    drawCircle(
                        color = Color.Magenta,
                        radius = with(density) { 25.dp.toPx() },
                        center = circlePosition
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val circleRadiusPx = with(density) { 25.dp.toPx() }
                    val randomX = Random.nextFloat() * (rectSize.width - 2 * circleRadiusPx) + circleRadiusPx
                    val randomY = Random.nextFloat() * (rectSize.height - 2 * circleRadiusPx) + circleRadiusPx
                    circlePosition = Offset(randomX, randomY)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Generar Posición")
            }
        }
    }
}

fun createRectanglePath(width: Float, height: Float, padding: Float): Path {
    return Path().apply {
        reset()
        moveTo(padding, padding)
        lineTo(width + padding, padding)
        lineTo(width + padding, height + padding)
        lineTo(padding, height + padding)
        close()
    }
}
