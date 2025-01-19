package com.example.gestoractividades.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.gestoractividades.ViewModel.VM_CreateTask.CrearTareaViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CrearTarea(
    viewModel: CrearTareaViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fechaHora by remember { mutableStateOf<Long?>(null) }
    val calendar = Calendar.getInstance()
    var imagenPath by remember { mutableStateOf<String?>(null) }

    // Configurar DatePickerDialog y TimePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(year, month, day)
            fechaHora = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            fechaHora = calendar.timeInMillis
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    // Configurar el ActivityResultLauncher para capturar la imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val savedPath = viewModel.guardarImagenLocal(bitmap)
            if (savedPath != null) {
                imagenPath = savedPath
            } else {
                Toast.makeText(context, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        Text(
            text = "Crear Nueva Tarea",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para seleccionar fecha y hora
        OutlinedTextField(
            value = fechaHora?.let {
                val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm",
                    Locale.getDefault())
                sdf.format(Date(it))
            } ?: "Sin Fecha",
            onValueChange = { },
            label = { Text("Fecha y Hora") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialog.show()
                    timePickerDialog.show()
                }) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Seleccionar fecha y hora"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { launcher.launch() }, // Lanzar cámara
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Capturar Imagen")
        }

        // Mostrar ruta de la imagen capturada
        imagenPath?.let {
            Text(text = "Imagen capturada: $it", style = MaterialTheme.typography.bodySmall)
        }

        Button(
            onClick = {
                viewModel.crearTarea(
                    titulo = titulo,
                    descripcion = descripcion,
                    imagenPath = imagenPath,
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Tarea creada exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBack()
                    },
                    onFailure = {
                        Toast.makeText(
                            context,
                            "Error al crear tarea",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = titulo.isNotEmpty() && descripcion.isNotEmpty()
        ) {
            Text(text = "Guardar Tarea")
        }

        Button(onClick = onBack) {
            Text(text = "Volver al Menú Principal")
        }
    }
}