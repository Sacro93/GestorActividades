package com.example.gestoractividades.EjemplosView

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestoractividades.R


@Composable
fun RegisterScreen(onRegister: () -> Unit, onBack: () -> Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)

        )

        {
            Image(
                painter = painterResource(id = R.drawable.icon_google),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {}, // Sin lógica
            shape = MaterialTheme.shapes.medium,
            label = {
                Text(
                    "Email", style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {}, // Sin lógica
            label = {
                Text(
                    "Password", style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
            shape = MaterialTheme.shapes.medium,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Show Password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {}, // Sin lógica

            label = {
                Text(
                    "Confirm Password", style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Show Password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(280.dp)
                .height(48.dp), // Ajusta el alto
            shape = RoundedCornerShape(12.dp), // Botones redondeados
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // Centrar verticalmente
                horizontalArrangement = Arrangement.Center, // Centrar horizontalmente
                modifier = Modifier.fillMaxSize() // Asegurar que el contenido ocupe todo el botón
            ) {
                Icon(
                    imageVector = Icons.Default.Check, // Reemplaza con tu ícono
                    contentDescription = "Icono de registro",
                    modifier = Modifier.size(24.dp) // Ajusta el tamaño del ícono
                )
                Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre ícono y texto
                Text("Register", style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(280.dp)
                .height(48.dp),

            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
               // Icon(
                   // imageVector = Icons.Default.ArrowBack,
                  //  contentDescription = "Icono de retroceso",
                  //  modifier = Modifier.size(24.dp)
              //  )
                Spacer(modifier = Modifier.width(4.dp)) // Espaciado entre ícono y texto
                Text(
                    "Back",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

