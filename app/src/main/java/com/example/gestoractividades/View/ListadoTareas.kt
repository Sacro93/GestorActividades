package com.example.gestoractividades.View

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import java.io.File

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (tareas.isEmpty()) {
            Text(
                text = "No hay tareas disponibles",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false) // El LazyColumn ocupa solo el espacio necesario
            ) {
                items(tareas) { tarea ->
                    ItemTarea(tarea = tarea,
                        onMarcarRealizada = { id ->
                            viewModel.marcarTareaComoRealizada(id = id,
                                onSuccess = {
                                    Toast.makeText(
                                        context, "Tarea marcada como realizada",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }, onFailure = { exception ->
                                    Toast.makeText(
                                        context,
                                        "Error al marcar como realizada: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }, onEliminarTarea = { id ->
                            viewModel.eliminarTarea(id = id, onSuccess = {
                                Toast.makeText(
                                    context, "Tarea eliminada", Toast.LENGTH_SHORT
                                ).show()
                            }, onFailure = { exception ->
                                Toast.makeText(
                                    context,
                                    "Error al eliminar tarea: ${exception.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                        })
                    Spacer(modifier = Modifier.height(16.dp)) // Separación entre tareas
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaciado dinámico antes del botón

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Altura uniforme para el botón
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A5D9F))
        ) {
            Text(
                "Volver al Menú Principal",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}


@Composable
fun ItemTarea(
    tarea: Tarea,
    onMarcarRealizada: (String) -> Unit,
    onEliminarTarea: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp), // Espaciado ajustado
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Mostrar imagen si existe
            if (!tarea.imagenPath.isNullOrEmpty()) {
                val file = File(tarea.imagenPath) // Cargar archivo desde la ruta
                if (file.exists()) {
                    Image(
                        painter = rememberAsyncImagePainter(file), // Usar la librería para cargar imágenes
                        contentDescription = "Imagen de la tarea",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp)) // Redondear bordes de la imagen
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop // Ajustar imagen al espacio
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            Text(
                text = tarea.titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) // Línea divisora

            Text(
                text = tarea.descripcion, style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) // Línea divisora

            Text(
                text = "Hora a realizar: ${
                    java.text.SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(
                        Date(tarea.fechaHora)
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) // Línea divisora

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onMarcarRealizada(tarea.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Marcar como realizada",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Realizada", color = Color.White)
                }

                Button(
                    onClick = { onEliminarTarea(tarea.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar tarea",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Eliminar", color = Color.White)
                }
            }
        }
    }
}

