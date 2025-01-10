package com.example.gestoractividades.ViewModel.VM_Session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestoractividades.Model.Autenticacion

class SessionActivaVMFactory(private val autenticacion: Autenticacion = Autenticacion()) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionActivaVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SessionActivaVM(autenticacion) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }
}