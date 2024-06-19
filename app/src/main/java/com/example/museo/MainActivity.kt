package com.example.museo
import android.content.ContentValues.TAG
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
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

            //firebaseManager.crearPintura("La Noche Estrellada", "Óleo sobre lienzo", "Vincent van Gogh", "img2.jpg")


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(firebaseManager: FirebaseManager) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
        drawerContent = {},
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Museo App") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        PinturasList(pinturas)
                    }
                }
            )
        }
    )
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
        elevation = CardDefaults.cardElevation(4.dp)
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
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = "Imagen de ${pintura.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MuseoTheme {
        MainScreen(FirebaseManager())
    }
}