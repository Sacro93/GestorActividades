package com.example.gestoractividades.ViewModel.VM_CreateTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestoractividades.Model.Autenticacion
import com.example.gestoractividades.Model.TareasRepository


class CrearTareaViewModelFactory(
    private val repository: TareasRepository,
    private val autenticacion: Autenticacion
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearTareaViewModel::class.java)) {
            return CrearTareaViewModel(repository, autenticacion) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

