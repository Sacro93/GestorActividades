/*package com.example.gestoractividades.EjemplosView



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestoractividades.Model.Tarea
import java.util.*

@Composable
fun ListadoTareasPreview() {
    val tareas = listOf(
        Tarea(
            id = "1",
            titulo = "Tarea 1",
            descripcion = "Descripción de la tarea 1",
            completada = false,
            fechaHora = System.currentTimeMillis()
        ),
        Tarea(
            id = "2",
            titulo = "Tarea 2",
            descripcion = "Descripción de la tarea 2",
            completada = false,
            fechaHora = System.currentTimeMillis() - 86400000L // Hace un día
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 32.dp) // Margen superior adicional
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(tareas) { tarea ->
                ItemTarea(
                    tarea = tarea,
                    onMarcarRealizada = {},
                    onEliminarTarea = {}
                )
                Spacer(modifier = Modifier.height(16.dp)) // Separación entre tareas
            }
        }

        Button(
            onClick = { /* Acción simulada */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)) // Violeta
        ) {
            Text("Volver al Menú Principal", color = Color.White)
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
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Título de la tarea
            Text(
                text = tarea.titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) // Línea divisora

            // Descripción
            Text(
                text = tarea.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) // Línea divisora

            // Hora a realizar
            Text(
                text = "Hora: ${
                    java.text.SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(
                        Date(tarea.fechaHora)
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) // Línea divisora

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onMarcarRealizada(tarea.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)) // Violeta
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)) // Violeta
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

@Preview(showBackground = true)
@Composable
fun PreviewListadoTareas() {
    ListadoTareasPreview()
}
*/