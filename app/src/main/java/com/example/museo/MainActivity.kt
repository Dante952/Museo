package com.example.museo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.museo.componets.CardGrid
import com.example.museo.componets.DrawerContent
import com.example.museo.componets.GoogleMapComposable
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



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MuseoTheme {
        MainScreen()
    }
}