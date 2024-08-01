package com.example.museo.componets.galleryMap

import android.graphics.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.nativeCanvas

@Preview(showBackground = true)
@Composable
fun GalleryScreen(onRoomSelected: (Int) -> Unit = {}) {
    val dotRects = ArrayList<Rect>()

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
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { tapOffset ->
                                var index = 0
                                for (rect in dotRects) {
                                    if (rect.contains(tapOffset.x.toInt(), tapOffset.y.toInt())) {
                                        onRoomSelected(index + 1)
                                        break
                                    }
                                    index++
                                }
                            }
                        )
                    }
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        // .clickable { onRoomSelected(0) }
                ) {
                    val padding = 0.dp.toPx()

                    val paint = Paint().apply {
                        color = android.graphics.Color.BLACK
                        textAlign = Paint.Align.CENTER
                        textSize = 50f
                    }

                    // Rectángulo 1 (arriba izquierda)
                    val rect1 = Rect(
                        padding.toInt(),
                        padding.toInt(),
                        (size.width / 3 - padding).toInt(),
                        (size.height / 4 - padding).toInt()
                    )
                    dotRects.add(rect1)
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
                    val rect2 = Rect(
                        (size.width / 2).toInt(),
                        padding.toInt(),
                        (size.width - padding).toInt(),
                        (size.height / 4 - padding).toInt()
                    )
                    dotRects.add(rect2)
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
                    val rect3 = Rect(
                        padding.toInt(),
                        (size.height / 3).toInt(),
                        (size.width / 3 - padding).toInt(),
                        (size.height / 3 + size.height / 4 - padding).toInt()
                    )
                    dotRects.add(rect3)
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
                    val rect4 = Rect(
                        (size.width * 2 / 3).toInt(),
                        (size.height / 4).toInt(),
                        (size.width - padding).toInt(),
                        (size.height / 4 + size.height / 6 - padding).toInt()
                    )
                    dotRects.add(rect4)
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
                    val rect5 = Rect(
                        (size.width / 2).toInt(),
                        (size.height * 5 / 12).toInt(),
                        (size.width - padding).toInt(),
                        (size.height * 5 / 12 + size.height / 6 - padding).toInt()
                    )
                    dotRects.add(rect5)
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
                val size = 24.sp
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