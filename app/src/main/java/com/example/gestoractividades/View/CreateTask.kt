package com.example.gestoractividades.View

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.gestoractividades.ViewModel.VM_CreateTask.CreateTaskState
import com.example.gestoractividades.ViewModel.VM_CreateTask.CreateTaskViewModel
import androidx.activity.result.contract.ActivityResultContracts


@Composable
fun CreateTask(
    onBack: () -> Unit,
    createTaskViewModel: CreateTaskViewModel
) {
    val createTaskState by createTaskViewModel.createTaskState.collectAsStateWithLifecycle()

    // Estados desde el ViewModel
    val nombre by createTaskViewModel.nombre.collectAsStateWithLifecycle()
    val descripcion by createTaskViewModel.descripcion.collectAsStateWithLifecycle()
    val fechaHora by createTaskViewModel.fechaHora.collectAsStateWithLifecycle()
    val selectedImage by createTaskViewModel.selectedImage.collectAsStateWithLifecycle()
    val calendar = Calendar.getInstance()
    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {

            val uri = createTaskViewModel.saveBitmapToInternalStorage(context, bitmap)
            uri?.let {
                createTaskViewModel.updateSelectedImage(it.toString())
            }
        }
    }
    LaunchedEffect(createTaskState) {
        if (createTaskState is CreateTaskState.Success) {
            Toast.makeText(context, "Tarea creada exitosamente", Toast.LENGTH_SHORT).show()
            onBack() // Regresa al HomeScreen
        }
    }

// DatePickerDialog
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, day ->
            val selectedDate = "$day/${month + 1}/$year"
            createTaskViewModel.updateFechaHora(
                "$selectedDate ${
                    createTaskViewModel.fechaHora.value.split(
                        " "
                    ).getOrNull(1) ?: ""
                }"
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

// TimePickerDialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            val selectedTime = "$hour:$minute"
            createTaskViewModel.updateFechaHora(
                "${
                    createTaskViewModel.fechaHora.value.split(" ").getOrNull(0) ?: ""
                } $selectedTime"
            )
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    ConfigureSystemBars()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            20.dp,
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal
    ) {
        // Campo de texto para el nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { createTaskViewModel.updateNombre(it) },
            label = { Text("Nombre de la tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para la descripción

        OutlinedTextField(
            value = descripcion,
            onValueChange = { createTaskViewModel.updateDescripcion(it) },
            label = { Text("Descripción de la tarea") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 200.dp),
            maxLines = 6,
            singleLine = false

        )

        // Selector para fecha y hora
        OutlinedTextField(
            value = fechaHora,
            onValueChange = { /* no es necesario actualizar directamente aquí */ },
            label = { Text("Fecha y hora") },
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


        // Espacio para cargar una imagen
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
                .clickable { launcher.launch() },// Abre la cámara
            contentAlignment = Alignment.Center
        ) {
            if (selectedImage != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedImage),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { launcher.launch() }
                    ) {
                        Text("Rehacer Foto")
                    }
                }
            } else {
                Text(
                    "Cargar Imagen",
                    color = Color.White,
                    modifier = Modifier.clickable { launcher.launch() } // Abre la cámara
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar la tarea
        Button(
            onClick = { createTaskViewModel.createTask() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar y crear  Tarea")
        }
        when (createTaskState) {
            is CreateTaskState.Success -> {
                LaunchedEffect(Unit) {
                    onBack()
                }
            }

            is CreateTaskState.Error -> {
                Text(
                    text = (createTaskState as CreateTaskState.Error).message,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            else -> Unit
        }

        // Botón para cancelar
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Cancelar")
        }
    }
}