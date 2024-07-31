package com.example.museo.componets.galleryMap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.museo.componets.ExpandedCard
import com.example.museo.data.PaintingData
import com.example.museo.ui.theme.MuseoTheme
import kotlin.math.sqrt


@Composable
fun RoomScreen(items: List<PaintingData>, onBack: () -> Unit = {}) {
    val FACTOR_X = remember { mutableStateOf(0.10) }
    val FACTOR_Y = remember { mutableStateOf(0.10) }
    val ROOM_LENGTH = 600.0
    val ROOM_HEIGHT = 600.0
    val pointX_position = remember { mutableStateOf(ROOM_LENGTH - 300) }
    val pointY_position = remember { mutableStateOf(ROOM_HEIGHT - 300) }

    val points = loadData()
    val canvas_length = distance(points[0], points[1])
    val canvas_height = distance(points[1], points[2])

    FACTOR_X.value = canvas_length / ROOM_LENGTH
    FACTOR_Y.value = canvas_height / ROOM_HEIGHT

    var showExpandedCard by remember { mutableStateOf(false) }
    var selectedPainting by remember { mutableStateOf<PaintingData?>(null) }

    MuseoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "SALÓN",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                ) {
                    Room(
                        data = points,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    User(
                        modifier = Modifier,
                        positionX = pointX_position.value,
                        positionY = pointY_position.value
                    )
                    items.forEachIndexed { index, painting ->
                        Image(
                            painter = rememberAsyncImagePainter(model = painting.imageUrl),
                            contentDescription = painting.name,
                            modifier = Modifier
                                .size(100.dp)
                                .offset(
                                    x = (index * 120).dp,
                                    y = (index * 120).dp
                                )
                                .clickable {
                                    selectedPainting = painting
                                    showExpandedCard = true
                                }
                        )
                    }
                    Button(
                        onClick = onBack,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        Text("Regresar al Mapa Principal")
                    }
                }
            }
        }
        if (showExpandedCard && selectedPainting != null) {
            ExpandedCard(
                index = items.indexOf(selectedPainting),
                content = selectedPainting!!,
                onDismiss = { showExpandedCard = false }
            )
        }
    }
}

fun distance(punto1: Point, punto2: Point): Float {
    val dx = punto2.x - punto1.x
    val dy = punto2.y - punto1.y
    return sqrt(dx * dx + dy * dy)
}

@Composable
fun loadData(): ArrayList<Point> {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeight = with(density) { configuration.screenHeightDp.dp.roundToPx() }
    val factor = screenHeight * 0.05f

    return arrayListOf(
        Point(0.5f * factor, 0.5f * factor), // inicio
        Point(9.2f * factor, 0.5f * factor), // x
        Point(9.2f * factor, 9.2f * factor),
        Point(0.5f * factor, 9.2f * factor), // y
        Point(0.5f * factor, 0.5f * factor)
    )
}