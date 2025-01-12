package com.example.gestoractividades.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.gestoractividades.ViewModel.VM_Home.HomeViewModel
import com.example.gestoractividades.ViewModel.VM_Session.SessionActivaVM

/*fun formatTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}*/

@Composable
fun HomeScreen(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToListTasks: () -> Unit,
    homeViewModel: HomeViewModel,
    sessionActivaVM: SessionActivaVM,
    logoutUser: () -> Unit
) {


    ConfigureSystemBars()
    val username by sessionActivaVM.username.collectAsState()

    LaunchedEffect(Unit) {
        sessionActivaVM.fetchCurrentUser()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        // Usuario y cierre de sesión
        UserLoad(
            username = username ?: "Usuario desconocido",
            logoutUser = {
                homeViewModel.cerrarSesion() // Llama al ViewModel para cerrar sesión
                logoutUser() // Navegación tras cerrar sesión
            }
        )
        // Opciones del menú
        OptionMenu(
            onNavigateToCreateTask = onNavigateToCreateTask,
            onNavigateToListTasks = onNavigateToListTasks
        )
    }
}

@Composable
fun UserLoad(
    username: String, // Recibe el nombre de usuario
    logoutUser: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { expanded = true } // Expande el menú al tocar la imagen
        ) {
            Image(
                painter = painterResource(id = R.drawable.mia),
                contentDescription = "foto usuario",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = username,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopStart) // Asegura que se alinee correctamente
        ) {
            DropdownMenuItem(
                text = { Text("Editar perfil") },
                onClick = {
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Cerrar sesión") },
                onClick = {
                    logoutUser()
                    expanded = false
                }
            )
        }
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
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para "Listado Tareas"
        Button(
            onClick = onNavigateToListTasks,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
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

