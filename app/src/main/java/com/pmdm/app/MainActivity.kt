package com.pmdm.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
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
                    color = MaterialTheme.colorScheme.background
                ) {MainView()}
            }
        }
    }
}

@Composable
fun MainView() {
    // Estado para controlar si se muestra la vista secundaria
    var showSecondaryView by remember { mutableStateOf(false) }

    Row {
        // Caja botón:
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            // Mostrar la vista secundaria si showSecondaryView es true
            if (showSecondaryView) {
                // Al cerrar la vista secundaria, actualiza el estado para ocultarla
                SecondaryView {showSecondaryView = false}
            // Botón para mostrar la vista secundaria al hacer clic.Al hacer clic, actualiza el estado para mostrar la vista secundaria
            } else {Button(onClick = {showSecondaryView = true},
                    modifier = Modifier
                        .height(50.dp)
                        .width(150.dp)
                ) {Text("Saludar")}
            }
        }
    }
    Row {
        // Caja textview:
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
            contentAlignment = Alignment.Center
        ) {Text(text = "TextVacuo")}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryView(onClose: () -> Unit) {
    // Aquí pondremos la ventana emergente
    // Al cerrar la vista secundaria, llama a la función onClose
    var name by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = {onClose()},
        modifier = Modifier.fillMaxWidth(),
        title = {Text("Configuracion")},
        text = {Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Etiqueta:
            Text("Introduce tu nombre")
            // Campo de texto para editar el nombre:
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Nombre")},
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )}
        },
        // Al hacer click en Aceptar,
        confirmButton = {TextButton(onClick = {}) {Text("Aceptar")}},
        // Al hacer click en Cancelar, cierra la vista secundaria (el dialog)
        dismissButton = {TextButton(onClick = {onClose()}) {Text("Cancelar")}}
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme {MainView()}
}