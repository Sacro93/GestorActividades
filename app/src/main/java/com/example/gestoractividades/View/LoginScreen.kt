package com.example.gestoractividades.View


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gestoractividades.Model.Autenticacion
import com.example.gestoractividades.R
import com.example.gestoractividades.ViewModel.VM_Login.LoginState
import com.example.gestoractividades.ViewModel.VM_Login.LoginViewModel
import com.example.gestoractividades.ViewModel.VM_Login.LoginViewModelFactory

@Composable
fun LoginScreen(
    onLogin: () -> Unit,   // Acción cuando se inicie sesión
    onRegister: () -> Unit, // Acción para ir al registro
    factory: LoginViewModelFactory = LoginViewModelFactory(Autenticacion())
) {
    // Crea el ViewModel usando la fábrica
    val loginViewModel: LoginViewModel = viewModel(factory = factory)

    // Observa el estado del ViewModel
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    // Estados para email y contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.icon_google), // Ajusta con tu recurso
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(100.dp) // Tamaño del logo
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text("Iniciar Sesión", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                Log.d("LoginScreen", "Email actualizado: $email")
            },
            label = { Text("Correo Electrónico") },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Correo")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Candado")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Iniciar Sesión"
        Button(
            onClick = { loginViewModel.loginUser(email, password) },
            enabled =  email.isNotBlank() && password.isNotBlank(),

            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) ,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(280.dp)
                .height(48.dp),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                "Iniciar Sesión",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Registrarse"
        TextButton(onClick = onRegister) {
            Text(
                "¿No tienes cuenta? Regístrate aquí.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Manejo de estados del ViewModel
        when (loginState) {
            is LoginState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                        .clickable(enabled = false) {}, // Evita que sea interactivo
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is LoginState.Error -> {
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            is LoginState.Success -> {
                // Llama al callback para navegar o realizar otra acción
                LaunchedEffect(Unit) {
                    onLogin()
                }
            }
            else -> Unit
        }
    }
}
