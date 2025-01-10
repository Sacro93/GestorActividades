package com.example.gestoractividades.View

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import com.example.gestoractividades.Model.Tarea
import com.example.gestoractividades.ViewModel.VM_ListadoTareas.ListadoTareasViewModel
import java.util.Date
import java.util.Locale

@Composable
fun ListadoTareas(
    viewModel: ListadoTareasViewModel,
    onBack: () -> Unit // Función para volver al menú principal
) {
    val tareas by viewModel.tareas // Obtenemos el estado de las tareas
    val context = LocalContext.current

    // Cargar las tareas al inicializar el Composable
    LaunchedEffect(Unit) {
        viewModel.cargarTareas()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Lista de tareas
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(tareas) { tarea ->
                ItemTarea(
                    tarea = tarea,
                    onMarcarRealizada = { id ->
                        viewModel.marcarTareaComoRealizada(
                            id = id,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Tarea marcada como realizada",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onFailure = { exception ->
                                Toast.makeText(
                                    context,
                                    "Error al marcar como realizada: ${exception.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    },
                    onEliminarTarea = { id ->
                        viewModel.eliminarTarea(
                            id = id,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Tarea eliminada",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onFailure = { exception ->
                                Toast.makeText(
                                    context,
                                    "Error al eliminar tarea: ${exception.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                )
            }
        }

        // Botón para volver al menú principal
        Button(
            onClick = onBack,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Volver al Menú Principal")
        }
    }
}



@Composable
fun ItemTarea(
    tarea: Tarea,
    onMarcarRealizada: (String) -> Unit, // Función para marcar como realizada
    onEliminarTarea: (String) -> Unit   // Función para eliminar tarea
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Contenido principal de la tarea
        Column(modifier = Modifier.weight(1f)) {
            Text(text = tarea.titulo, style = MaterialTheme.typography.titleMedium)
            Text(text = tarea.descripcion, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Fecha: ${
                    java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(
                        Date(tarea.fechaHora)
                    )
                }",
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Botones de acción
        Column {
            Button(onClick = { onMarcarRealizada(tarea.id) }) {
                Text("Realizada")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onEliminarTarea(tarea.id) }) {
                Text("Eliminar")
            }
        }
    }
}

