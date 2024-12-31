package com.example.gestoractividades.ViewModel.VM_Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestoractividades.Model.Autenticacion
import com.google.firebase.auth.FirebaseAuth

/*Tu LoginViewModel depende de FirebaseAuth y lo inicializas directamente en la clase.
Esto puede ser problemático para pruebas o cambios futuros. Deberías usar una ViewModelProvider.Factory para inyectar dependencias.*/
class LoginViewModelFactory(private val autenticacion: Autenticacion) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(autenticacion) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }
}
