package com.example.gestoractividades.ViewModel.VM_Session

import androidx.lifecycle.ViewModel
import com.example.gestoractividades.Model.Autenticacion
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asStateFlow


class SessionActivaVM(
    private val autenticacion: Autenticacion
) : ViewModel() {

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    fun fetchCurrentUser() {
        _userEmail.value = autenticacion.getCurrentUser()
    }

    /*sirve para coordinar acciones relacionadas con la sesión del usuario en tu aplicación, como:
    *
    *lamar a logoutUser de Autenticacion.
    *limpiar el estado local (_userEmail).
    *
    *activar cualquier lógica reactiva necesaria para que la UI responda al cambio en el estado de sesión
    * */
    fun cerrarSesion() {
        autenticacion.logoutUser()
        _userEmail.value = null // Limpia el estado del usuario activo
    }
}
