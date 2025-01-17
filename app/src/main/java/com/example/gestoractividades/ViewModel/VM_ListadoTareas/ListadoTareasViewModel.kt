package com.example.gestoractividades.ViewModel.VM_ListadoTareas

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.gestoractividades.Model.Tarea
import com.example.gestoractividades.Model.TareasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.State
import com.example.gestoractividades.Model.Autenticacion

class ListadoTareasViewModel(private val repository: TareasRepository,
                             private val autenticacion: Autenticacion
) : ViewModel() {

    private val _tareas = mutableStateOf<List<Tarea>>(emptyList())
    val tareas: State<List<Tarea>> get() = _tareas

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    fun cargarTareas() {
        viewModelScope.launch {
            try {
                val userId = autenticacion.getCurrentUserId()
                    ?: throw Exception("No se pudo obtener el ID del usuario. Intente iniciar sesión nuevamente.")
                repository.obtenerTareas(
                    userId = userId,
                    onSuccess = { tareas ->
                        _tareas.value = tareas
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun obtenerTareaPorId(id: String, onSuccess: (Tarea?) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                val tarea = repository.obtenerTareaPorId(id)
                onSuccess(tarea)
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    fun marcarTareaComoRealizada(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                repository.marcarTareaComoRealizada(id)
                onSuccess()
                cargarTareas() // Refrescar la lista
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    fun eliminarTarea(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                repository.eliminarTarea(id)
                onSuccess()
                cargarTareas() // Refrescar la lista
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}


