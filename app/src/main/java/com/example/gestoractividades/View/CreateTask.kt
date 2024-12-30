package com.example.gestoractividades.View

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable

fun CreateTask(
    onBack: () -> Unit
) {
    ConfigureSystemBars()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            20.dp,
            Alignment.CenterVertically
        ), // Espaciado uniforme
        horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal
    ) {
        // Campo de texto para el nombre
        OutlinedTextField(
            value = "",
            onValueChange = { /* Actualizar estado */ },
            label = { Text("Nombre de la tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de texto para la descripción
        OutlinedTextField(
            value = "",
            onValueChange = { /* Actualizar estado */ },
            label = { Text("Descripción de la tarea") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 200.dp), // Rango de altura
            maxLines = 6,// Permitir varias líneas
            singleLine = false // Permitir múltiples líneas

        )

        // Selector para fecha y hora (marcador de posición)
        OutlinedTextField(
            value = "",
            onValueChange = { /* Actualizar estado */ },
            label = { Text("Fecha y hora") },
            readOnly = true, // Evitar que se edite directamente
            trailingIcon = {
                IconButton(onClick = { /* Abrir selector de fecha/hora */ }) {
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
                .clickable { /* Lógica para cargar imagen */ },
            contentAlignment = Alignment.Center
        ) {
            Text("Cargar Imagen", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar la tarea
        Button(
            onClick = { /* Guardar tarea */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar Tarea")
        }


        // Botón para guardar la tarea
        Button(
            onClick = { /* Guardar tarea */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Cancelar")
        }
    }
}