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
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun GalleryMap() {
    // Estado para almacenar la posición del círculo
    var circlePosition by remember { mutableStateOf(Offset(50f, 50f)) }

    // Estado para almacenar el tamaño del mapa
    var mapSize by remember { mutableStateOf(Size.Zero) }

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
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    mapSize = Size(canvasWidth, canvasHeight)

                    // Dibujar el mapa (similar al proporcionado en la imagen)
                    drawContext.canvas.drawPath(
                        path = createMapPath(canvasWidth, canvasHeight, paddingPx),
                        paint = Paint().apply {
                            color = Color.Black
                            style = PaintingStyle.Stroke
                            strokeWidth = paddingPx
                        }
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
                    val randomX = Random.nextFloat() * (mapSize.width - 2 * circleRadiusPx) + circleRadiusPx
                    val randomY = Random.nextFloat() * (mapSize.height - 2 * circleRadiusPx) + circleRadiusPx
                    circlePosition = Offset(randomX, randomY)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Generar Posición")
            }
        }
    }
}

fun createMapPath(width: Float, height: Float, padding: Float): Path {
    return Path().apply {
        reset()

        // Dibujar el contorno del mapa principal
        moveTo(padding, padding)
        lineTo(width - padding, padding)
        lineTo(width - padding, height - padding)
        lineTo(padding, height - padding)
        close()

        // Dibujar las habitaciones internas
        val roomPadding = padding * 2
        val roomWidth = (width - roomPadding * 2) / 3
        val roomHeight = (height - roomPadding * 2) / 3

        // Habitación 1
        moveTo(roomPadding, roomPadding)
        lineTo(roomPadding + roomWidth, roomPadding)
        lineTo(roomPadding + roomWidth, roomPadding + roomHeight)
        lineTo(roomPadding, roomPadding + roomHeight)
        close()

        // Habitación 2
        moveTo(roomPadding + roomWidth + roomPadding, roomPadding)
        lineTo(roomPadding + roomWidth * 2 + roomPadding, roomPadding)
        lineTo(roomPadding + roomWidth * 2 + roomPadding, roomPadding + roomHeight)
        lineTo(roomPadding + roomWidth + roomPadding, roomPadding + roomHeight)
        close()

        // Habitación 4
        moveTo(roomPadding, roomPadding + roomHeight + roomPadding)
        lineTo(roomPadding + roomWidth, roomPadding + roomHeight + roomPadding)
        lineTo(roomPadding + roomWidth, roomPadding + roomHeight * 2 + roomPadding)
        lineTo(roomPadding, roomPadding + roomHeight * 2 + roomPadding)
        close()

        // Habitación 5
        moveTo(roomPadding + roomWidth + roomPadding, roomPadding + roomHeight + roomPadding)
        lineTo(roomPadding + roomWidth * 2 + roomPadding, roomPadding + roomHeight + roomPadding)
        lineTo(roomPadding + roomWidth * 2 + roomPadding, roomPadding + roomHeight * 2 + roomPadding)
        lineTo(roomPadding + roomWidth + roomPadding, roomPadding + roomHeight * 2 + roomPadding)
        close()

    }
}




