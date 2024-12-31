package com.example.gestoractividades.ViewModel.VM_Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestoractividades.Model.Autenticacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data object Success : RegisterState()
    data class Error(val message: String) : RegisterState()

}

class RegisterViewModel(
    private val autenticacion: Autenticacion = Autenticacion() // Inicialización directa

) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()


    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z]).{8,}$") // Al menos una mayúscula y 8 caracteres
        return regex.matches(password)
    }
    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}\$")
        return regex.matches(email)
    }

    fun registerUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _registerState.value = RegisterState.Error("Los campos no pueden estar vacios")
            return
        }
        if (!isValidEmail(email)) {
            _registerState.value = RegisterState.Error("Correo electrónico no válido")
            return
        }
        if (!isValidPassword(password)) {
            _registerState.value = RegisterState.Error(
                "La contraseña debe tener al menos 8 caracteres y contener al menos una letra mayúscula."
            )
            return
        }

        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            val result = autenticacion.registerUser(email, password)
            _registerState.value = if (result.isSuccess) {
                RegisterState.Success
            } else {
                RegisterState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }
}