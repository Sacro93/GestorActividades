package com.example.gestoractividades.ViewModel.VM_Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestoractividades.Model.Autenticacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
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

    private fun isValidUsername(username: String): Boolean {
        if (username.isBlank()) return false
        val regex = Regex("^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)+\$") // Acepta letras con acentos
        return regex.matches(username)
    }



    fun registerUser(email: String, password: String,username: String) {
        if (email.isBlank() || password.isBlank()|| username.isBlank()) {
            _registerState.value = RegisterState.Error("Los campos no pueden estar vacios")
            return
        }
        if (!isValidEmail(email)) {
            _registerState.value = RegisterState.Error("Correo electrónico no válido")
            return
        }
        if (!isValidUsername(username)) {
            _registerState.value =
                RegisterState.Error("El nombre de usuario debe ser tu nombre y apellido")
            return
        }
        if (!isValidPassword(password)) {
            _registerState.value = RegisterState.Error(
                "La contraseña debe tener al menos 8 caracteres y contener al menos una letra mayúscula."
            )
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
            val result = autenticacion.registerUser(email, password,username)
            if (result.isSuccess) {
                val userId = autenticacion.getCurrentUserId()
                if (userId != null) {
                    val saveResult = saveUserDetails(userId, username, email)
                    _registerState.value = if (saveResult) {
                        RegisterState.Success
                    } else {
                        RegisterState.Error("Error al guardar los datos del usuario.")
                    }
                } else {
                    _registerState.value =
                        RegisterState.Error("Error al obtener el ID del usuario.")
                }
            } else {
                _registerState.value =
                    RegisterState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    private suspend fun saveUserDetails(userId: String, username: String, email: String): Boolean {
        return try {
            val firestore = FirebaseFirestore.getInstance()
            val userData = mapOf(
                "id" to userId,
                "username" to username,
                "email" to email,
                "createdAt" to Timestamp.now()
            )
            firestore.collection("Users").document(userId).set(userData).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
