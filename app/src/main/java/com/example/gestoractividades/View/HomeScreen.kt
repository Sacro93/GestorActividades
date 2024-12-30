package com.example.gestoractividades.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gestoractividades.R


@Composable
fun HomeScreen(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToListTasks: () -> Unit

) {
    ConfigureSystemBars()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(Color.White),

        //verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre composables
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        UserLoad()
        OptionMenu(
            onNavigateToCreateTask = onNavigateToCreateTask,
            onNavigateToListTasks = onNavigateToListTasks
        )
        RecentActivity()
    }
}


@Composable
fun UserLoad() {

    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable


    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.mia),
            contentDescription = "foto usuario",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable { expanded = true }

        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            "usuario",
            style = MaterialTheme.typography.bodyMedium
        )
    }


    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }// Cierra el menú al hacer clic afuera
    ) {
        DropdownMenuItem(
            text = { Text("Editar perfil") },
            onClick = {
                expanded = false
            }
        )
        DropdownMenuItem(
            text = { Text("Cerrar sesion") },
            onClick = {
                //accion cerrar sesion
                expanded = false
            }
        )
    }
}

@Composable
fun OptionMenu(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToListTasks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = onNavigateToCreateTask,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "Crear tarea",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge, // Tipografía estándar para botones
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para "Listado Tareas"
        Button(
            onClick = onNavigateToListTasks,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Tamaño uniforme
            shape = RoundedCornerShape(16.dp), // Botones más cuadrados con esquinas redondeadas
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "Listado Tareas",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun RecentActivity() {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Espaciado interno
        ) {
            // Imagen
            Image(
                painter = painterResource(id = R.drawable.platos_),
                contentDescription = "Imagen del evento",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp)) // Separación entre imagen y texto

            // Título
            Text(
                text = "Lavar los platos", // Título principal
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Texto descriptivo con hora
            Text(
                text = "Hora del evento: 14:30", // Aquí incluyes la hora del evento
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espaciado antes de los botones

            // Acciones (botones)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Alinea los botones al final
            ) {
                // Botón "Marcar como hecho"
                Button(
                    onClick = { /* Acción para marcar como hecho */ },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp) // Esquinas redondeadas
                ) {
                    Icon(
                        imageVector = Icons.Default.Check, // Ícono de realizado
                        contentDescription = "Marcar como hecho",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre botones

                // Botón "Anular"
                Button(
                    onClick = { /* Acción para anular */ },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp) // Esquinas redondeadas
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, // Ícono de tacho de basura
                        contentDescription = "Anular evento",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}







