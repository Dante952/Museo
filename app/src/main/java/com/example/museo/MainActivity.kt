package com.example.museo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.museo.componets.CardGrid
import com.example.museo.componets.DrawerContent
import com.example.museo.ui.theme.MuseoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val firebaseManager = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuseoTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedScreen by remember { mutableStateOf("Home") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(onItemSelected = { label ->
                    selectedScreen = label
                    scope.launch { drawerState.close() }
                })
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("App Museo") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },

                content = { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        when (selectedScreen) {
                            "Home" -> HomeScreen()
                            "Location" -> LocationScreen()
                            "About" -> AboutScreen()
                        }
                    }
                }
            )
        }
    )
}


@Composable
fun HomeScreen() {
    val items = List(20) { index -> "Item $index" }
    CardGrid(items = items)
}

@Composable
fun LocationScreen() {
    GoogleMapComposable()
}



@Composable
fun AboutScreen() {
    Text(text = "About Screen", modifier = Modifier.padding(16.dp))
}

@Composable
fun PinturasList(pinturas: List<PaintingData>) {
    LazyColumn {
        items(pinturas) { pintura ->
            PinturaItem(pintura)
        }
    }
}

@Composable
fun PinturaItem(pintura: PaintingData) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Título: ${pintura.name}", style = MaterialTheme.typography.titleSmall)
            Text(text = "Autor: ${pintura.author}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Descripción: ${pintura.description}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            pintura.imageUrl?.let { url ->
                Image(
                    painter = rememberImagePainter(url),
                    contentDescription = "Imagen de ${pintura.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Botón de reproducción de audio
                IconButton(
                    onClick = {
                        // Aquí implementar la lógica de reproducción de audio
                        pintura.audioUrl?.let { audioUrl ->
                            Log.d("PinturaItem", "Reproduciendo audio: $audioUrl")
                            //implementar la lógica para reproducir el audio

                        }
                    }
                ) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = "Reproducir")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reproducir audio", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MuseoTheme {
        MainScreen()
    }
}