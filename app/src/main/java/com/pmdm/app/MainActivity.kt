package com.pmdm.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.app.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var acceptCounter by remember { mutableStateOf(0) }
    var cancelCounter by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Caja botón:
        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable { showDialog = true },
            contentAlignment = Alignment.Center
        ) {
            // Botón
            Text(
                text = "Saludar"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Caja textview:
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Texto MainView para saludar cuando pongan un valor.
            Text(text = if (name.isNotEmpty()) "Hola, $name" else "")
        }

        // Ventana de diálogo
        if (showDialog) {
            SecondaryView(
                name = name,
                onAccept = {
                    acceptCounter++
                    name = it
                    showDialog = false
                },
                onCancel = {
                    cancelCounter++
                    name = ""
                    showDialog = false
                },
                onClear = { name = "" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contador de botones
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "A$acceptCounter C$cancelCounter",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryView(
    name: String,
    onAccept: (String) -> Unit,
    onCancel: () -> Unit,
    onClear: () -> Unit
) {
    var newName by remember { mutableStateOf(name) }

    AlertDialog(
        onDismissRequest = {
            onClear()
            onCancel()
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Configuración",
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Introduce tu nombre",
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo de texto para editar el nombre:
                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    placeholder = { Text("Nombre") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAccept(newName)
                }
            ) {
                Text("Aceptar")
            }
        },

        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme {
        MainView()
    }
}
