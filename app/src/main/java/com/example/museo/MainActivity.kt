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
import com.example.museo.data.FirebaseManager
import com.example.museo.data.PaintingData
import com.example.museo.ui.theme.MuseoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val firebaseManager = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuseoTheme {
                MainScreen(firebaseManager)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(firebaseManager: FirebaseManager) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedScreen by remember { mutableStateOf("Home") }
    var currentGalleryScreen by remember { mutableStateOf("Gallery") }

    var pinturas by remember { mutableStateOf<List<PaintingData>>(emptyList()) }

    LaunchedEffect(Unit) {
        firebaseManager.obtenerPinturas(
            onSuccess = { listaPinturas ->
                pinturas = listaPinturas
                // Log para verificar los datos obtenidos
                listaPinturas.forEach {
                    Log.d("MainScreen", "Pintura: $it")
                }
            },
            onFailure = { exception ->
                Log.e("MainScreen", "Error al obtener pinturas", exception)
            }
        )
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
                            "Home" -> HomeScreen(firebaseManager)
                            "Location" -> when (currentGalleryScreen) {
                                "Gallery" -> GalleryScreen { roomId ->
                                    currentGalleryScreen = "Room"
                                }
                                "Room" -> RoomScreen(items = pinturas) {
                                    currentGalleryScreen = "Gallery"
                                }
                            }
                            "About" -> AboutScreen()
                        }
                    }
                }
            )
        }
    )
}


@Composable
fun HomeScreen(firebaseManager: FirebaseManager) {

    var pinturas by remember { mutableStateOf<List<PaintingData>>(emptyList()) }

    LaunchedEffect(Unit) {
        firebaseManager.obtenerPinturas(
            onSuccess = { listaPinturas ->
                pinturas = listaPinturas
                // Log para verificar los datos obtenidos
                listaPinturas.forEach {
                    Log.d("MainScreen", "Pintura: $it")
                }
            },
            onFailure = { exception ->
                Log.e("MainScreen", "Error al obtener pinturas", exception)
            }
        )
    }
    CardGrid(items = pinturas)
}

@Composable
fun LocationScreen() {
    GoogleMapComposable()
}



@Composable
fun AboutScreen() {
    Text(text = "About Screen", modifier = Modifier.padding(16.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MuseoTheme {
        MainScreen(FirebaseManager())
    }
}