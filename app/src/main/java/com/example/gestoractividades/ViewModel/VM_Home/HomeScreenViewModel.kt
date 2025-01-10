package com.example.gestoractividades.ViewModel.VM_Home


import androidx.lifecycle.ViewModel

import com.example.gestoractividades.ViewModel.VM_Session.SessionActivaVM



class HomeViewModel(
    private val sessionActivaVM: SessionActivaVM
) : ViewModel() {

    // Cerrar sesión del usuario
    fun cerrarSesion() {
        sessionActivaVM.cerrarSesion()
    }
}



