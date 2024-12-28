package com.example.gestoractividades.View

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestoractividades.R
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Preview
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre composables
    ) {
        UserLoad()
        OptionMenu()
        //RecentActivity()
    }
}


@Composable
fun UserLoad() {

    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable

    Spacer(modifier = Modifier.width(8.dp))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionMenu() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Tamaño más grande para un diseño destacado
            shape = RoundedCornerShape(8.dp), // Botones más cuadrados con esquinas redondeadas
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
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Tamaño uniforme
            shape = RoundedCornerShape(8.dp), // Botones más cuadrados con esquinas redondeadas
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

}





