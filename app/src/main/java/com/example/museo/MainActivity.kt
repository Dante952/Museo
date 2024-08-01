package com.example.museo

import android.os.Bundle
import android.util.Log
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
import coil.compose.rememberImagePainter
import com.example.museo.componets.CardGrid
import com.example.museo.componets.DrawerContent
import com.example.museo.componets.GoogleMapComposable
import com.example.museo.componets.galleryMap.GalleryScreen
import com.example.museo.componets.galleryMap.RoomScreen
import com.example.museo.data.Pintura
import com.example.museo.data.RetrofitClient
import com.example.museo.ui.theme.MuseoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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
    var currentGalleryScreen by remember { mutableStateOf("Gallery") }

    var selectedScreen by remember { mutableStateOf("Catalogo") }
    var pinturas by remember { mutableStateOf<List<Pintura>>(emptyList()) }
    val apiService = RetrofitClient.apiService
    var selectedRoom by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        try {
            // Llama a la función suspendida
            var response = apiService.getPinturas()
            pinturas = response
            Log.d("HomeScreen", "Pinturas obtenidas: ${pinturas.size}")
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error al obtener pinturas", e)
        }
    }

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
                            "Catalogo" -> HomeScreen()
                            "Galería" -> when (currentGalleryScreen) {
                                "Gallery" -> GalleryScreen { roomId ->
                                    selectedRoom = roomId
                                    currentGalleryScreen = "Room"
                                }
                                "Room" -> RoomScreen(items = pinturas, roomId = selectedRoom) {
                                    currentGalleryScreen = "Gallery"
                                }
                            }
                            "Mapa GPS" -> LocationScreen()
                            "Escaner QR" -> QRScannerScreen()
                        }
                    }
                }
            )
        }
    )
}


@Composable
fun HomeScreen() {

    var pinturas by remember { mutableStateOf<List<Pintura>>(emptyList()) }
    val apiService = RetrofitClient.apiService

    LaunchedEffect(Unit) {
        try {
            // Llama a la función suspendida
            var response = apiService.getPinturas()
            pinturas = response
            Log.d("HomeScreen", "Pinturas obtenidas: ${pinturas.size}")
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error al obtener pinturas", e)
        }
    }
    CardGrid(items = pinturas)
}

@Composable
fun LocationScreen() {
    GoogleMapComposable()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MuseoTheme {
        MainScreen()
    }
}