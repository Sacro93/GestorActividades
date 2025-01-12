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

    fun fetchCurrentUser() {
        val userId = autenticacion.getCurrentUserId() // Obtener ID del usuario autenticado
        if (userId != null) {
            viewModelScope.launch {
                val result = autenticacion.getUserDetails(userId)
                if (result.isSuccess) {
                    val userDetails = result.getOrNull()
                    _userEmail.value = userDetails?.get("email") as? String
                    _username.value = userDetails?.get("username") as? String
                } else {
                    _userEmail.value = "Usuario desconocido"
                    _username.value = "Usuario desconocido"
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

