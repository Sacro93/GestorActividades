package com.example.gestoractividades.ViewModel.VM_Session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestoractividades.Model.Autenticacion
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*sirve para coordinar acciones relacionadas con la sesión del usuario en tu aplicación, como:
    *
    *lamar a logoutUser de Autenticacion.
    *limpiar el estado local (_userEmail).
    *
    *activar cualquier lógica reactiva necesaria para que la UI responda al cambio en el estado de sesión
    * */


class SessionActivaVM(
    private val autenticacion: Autenticacion
) : ViewModel() {

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()

    /*
        fun fetchCurrentUser() {
            val userId = autenticacion.getCurrentUserId() // Obtener ID del usuario autenticado
            if (userId != null) {
                viewModelScope.launch {
                    try {
                        val result = autenticacion.getUserDetails(userId)
                        if (result.isSuccess) {
                            val userDetails = result.getOrNull()
                            _userEmail.value = userDetails?.get("email") as? String ?: "Email no disponible"
                            _username.value = userDetails?.get("username") as? String ?: "Nombre no disponible"
                        } else {
                            // Si el resultado falla, asignar valores predeterminados
                            _userEmail.value = "Usuario desconocido"
                            _username.value = "Usuario desconocido"
                        }
                    } catch (e: Exception) {
                        // Manejo de errores en caso de excepciones inesperadas
                        _userEmail.value = "Error al obtener email"
                        _username.value = "Error al obtener nombre"
                    }
                }
            } else {
                // Si no hay usuario autenticado, asignar valores predeterminados
                _userEmail.value = "Usuario no autenticado"
                _username.value = "Usuario no autenticado"
            }
            */
    fun fetchCurrentUser() {
        val userId = autenticacion.getCurrentUserId()
        if (userId != null) {
            viewModelScope.launch {
                val result = autenticacion.getUserDetails(userId)
                if (result.isSuccess) {
                    val userDetails = result.getOrNull()
                    _username.value = userDetails?.get("username") as? String
                    _userEmail.value = userDetails?.get("email") as? String
                } else {
                    _username.value = "Usuario desconocido"
                    _userEmail.value = null
                }
            }
        }
    }


    fun cerrarSesion() {
        autenticacion.logoutUser()
        _userEmail.value = null
        _username.value = null
    }
}

