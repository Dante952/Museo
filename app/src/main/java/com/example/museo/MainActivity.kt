package com.example.museo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.museo.componets.CardGrid
import com.example.museo.componets.DrawerContent
import com.example.museo.data.FirebaseManager
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
        // Ejemplo: Subir datos de pintura al presionar un botón o en algún evento
        //val titulo = "tituloTEST"
        //val descripcion = "descripcionTEST"

        //firebaseManager.subirDatosPintura(titulo, descripcion)
        obtenerYMostrarPinturas()
    }
    private fun obtenerYMostrarPinturas() {
        firebaseManager.obtenerPinturas(
            onSuccess = { listaPinturas ->
                // Aquí puedes manejar la lista de pinturas obtenidas exitosamente
                for (pintura in listaPinturas) {
                    Log.d(TAG, "Pintura: ${pintura.name}, Autor: ${pintura.author}, Descripción: ${pintura.description}")
                }
            },
            onFailure = { exception ->
                Log.e(TAG, "Error al obtener pinturas", exception)
                // Manejar el error, por ejemplo mostrando un mensaje al usuario
            }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }

    val items = List(20) { index -> "Item $index" }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent()
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("My Compose App") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },

                content = { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        CardGrid(items)
                    }
                }
            )
        }
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MuseoTheme {
        MainScreen()
    }
}