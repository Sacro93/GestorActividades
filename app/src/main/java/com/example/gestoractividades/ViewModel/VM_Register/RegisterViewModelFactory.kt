package com.example.gestoractividades.ViewModel.VM_Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestoractividades.Model.Autenticacion


/*¿Por qué lo usamos?
Usar una fábrica (factory) asegura que el RegisterViewModel pueda recibir dependencias externas (como FirebaseAuth) sin inicializarlas directamente dentro del ViewModel.

Esto facilita el uso de pruebas unitarias, ya que puedes inyectar versiones
"mock" o simuladas de FirebaseAuth durante las pruebas.*/
class RegisterViewModelFactory(private val autenticacion: Autenticacion) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(autenticacion) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }
}
