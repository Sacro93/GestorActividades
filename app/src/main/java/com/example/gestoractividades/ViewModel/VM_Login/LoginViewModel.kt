package com.example.gestoractividades.ViewModel.VM_Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestoractividades.Model.Autenticacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


// Estados para el inicio de sesión
sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(
    private val autenticacion: Autenticacion = Autenticacion() // Inicialización directa

) : ViewModel() {
    // Estado del inicio de sesión
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // Función para manejar el inicio de sesión
    fun loginUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Los campos no pueden estar vacíos")
            return
        }

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val resultado = autenticacion.loginUser(email, password)
            _loginState.value = if (resultado.isSuccess) {
                LoginState.Success
            } else {
                LoginState.Error(resultado.exceptionOrNull()?.message ?: "Error desconocido")
            }

        }
    }
}


