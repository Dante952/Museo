package com.example.museo.componets.galleryMap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun GalleryScreen() {
    // Estado para almacenar la posición del círculo
    var circlePosition by remember { mutableStateOf(Offset(0f, 0f)) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0099FF))
            .padding(0.dp) // Ajusta el padding según sea necesario
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
                    .fillMaxSize()
                    .background(Color(0xFF0099FF))
                    .padding(16.dp) // Ajusta el padding según sea necesario
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    // Canvas para dibujar el rectángulo negro y el círculo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(0.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val padding = 0.dp.toPx() // Ajusta el padding según sea necesario

                            // Rectángulo 1 (arriba izquierda)
                            drawRect(
                                color = Color.Black,
                                topLeft = Offset(padding, padding),
                                size = Size(
                                    size.width / 4 - padding,
                                    size.height / 4 - padding
                                )
                            )

                            // Rectángulo 2 (arriba derecha)
                            drawRect(
                                color = Color.Black,
                                topLeft = Offset(size.width / 2, padding),
                                size = Size(
                                    size.width / 2 - padding,
                                    size.height / 4 - padding
                                )
                            )

                            // Rectángulo 3 (abajo izquierda)
                            drawRect(
                                color = Color.White,
                                topLeft = Offset(padding, size.height / 3),
                                size = Size(
                                    size.width / 4 - padding,
                                    size.height / 4 - padding
                                )
                            )

                            // Rectángulo 4 (abajo centro)
                            drawRect(
                                color = Color.Blue,
                                topLeft = Offset(size.width * 2 / 3, size.height ),
                                size = Size(
                                    size.width / 3 - padding,
                                    size.height / 2 - padding
                                )
                            )

                            // Rectángulo 5 (abajo derecha)
                            drawRect(
                                color = Color.Red,
                                topLeft = Offset(size.width / 2, size.height / 2),
                                size = Size(
                                    size.width / 2 - padding,
                                    size.height / 4 - padding
                                )
                            )
                        }
                    }
                    // Espacio para separar elementos
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}