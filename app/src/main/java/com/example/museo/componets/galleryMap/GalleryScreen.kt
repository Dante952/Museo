package com.example.museo.componets.galleryMap

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun GalleryScreen(onRoomSelected: (Int) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "MAPA DE LA GALERÍA",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color.Transparent)
                    .padding(16.dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onRoomSelected(1) }
                ) {
                    val padding = 0.dp.toPx()

                    val paint = Paint().apply {
                        color = android.graphics.Color.BLACK
                        textAlign = Paint.Align.CENTER
                        textSize = 50f
                    }

                    // Rectángulo 1 (arriba izquierda)
                    drawRect(

                        color = Color.Black,
                        topLeft = Offset(padding, padding),
                        size = Size(
                            size.width / 3 - padding,
                            size.height / 4 - padding
                        ),
                        style = Stroke(width = 10f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "Salón 1",
                        padding + (size.width / 6),
                        padding + (size.height / 8),
                        paint
                    )

                    // Rectángulo 2 (arriba derecha)
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(size.width / 2, padding),
                        size = Size(
                            size.width / 2 - padding,
                            size.height / 4 - padding
                        ),
                        style = Stroke(width = 10f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "Salón 2",
                        size.width * 3 / 4,
                        padding + (size.height / 8),
                        paint
                    )

                    // Rectángulo 3 (abajo izquierda)
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(padding, size.height / 3),
                        size = Size(
                            size.width / 3 - padding,
                            size.height / 4 - padding
                        ),
                        style = Stroke(width = 10f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "Salón 3",
                        padding + (size.width / 6),
                        size.height / 3 + (size.height / 8),
                        paint
                    )

                    // Rectángulo 4 (derecha centro)
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(size.width * 2 / 3, size.height / 4),
                        size = Size(
                            size.width / 3 - padding,
                            size.height / 6 - padding
                        ),
                        style = Stroke(width = 10f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "Salón 4",
                        size.width * 5 / 6,
                        size.height / 4 + (size.height / 12),
                        paint
                    )

                    // Rectángulo 5 (abajo derecha)
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(size.width / 2, size.height * 5 / 12),
                        size = Size(
                            size.width / 2 - padding,
                            size.height / 6 - padding
                        ),
                        style = Stroke(width = 10f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "Salón 5",
                        size.width * 3 / 4,
                        size.height * 5 / 12 + (size.height / 12),
                        paint
                    )
                }
            }
            // Leyenda
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                var size = 24.sp
                Text("Leyenda:", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text("Salón 1: Pinturas", fontSize = size)
                Text("Salón 2: Estatuas", fontSize = size)
                Text("Salón 3: Lienzos", fontSize = size)
                Text("Salón 4: Fotografías", fontSize = size)
                Text("Salón 5: Esculturas", fontSize = size)
            }
        }
    }
}