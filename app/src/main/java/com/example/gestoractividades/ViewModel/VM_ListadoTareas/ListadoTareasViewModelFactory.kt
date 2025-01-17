package com.example.gestoractividades.ViewModel.VM_ListadoTareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestoractividades.Model.Autenticacion
import com.example.gestoractividades.Model.TareasRepository

class ListadoTareasViewModelFactory(
    private val tareasRepository: TareasRepository,
    private val autenticacion: Autenticacion
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListadoTareasViewModel::class.java)) {
            return ListadoTareasViewModel(tareasRepository, autenticacion) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}