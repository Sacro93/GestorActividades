package com.example.gestoractividades.ViewModel.VM_CreateTask

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestoractividades.Model.Autenticacion
import com.example.gestoractividades.Model.TareasRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date


class CrearTareaViewModel(
    private val repository: TareasRepository,
    private val autenticacion: Autenticacion
) : ViewModel() {

    private val _fechaHora = MutableStateFlow<Long?>(null)
    val fechaHora: StateFlow<Long?> = _fechaHora.asStateFlow()

    fun updateFechaHora(timeInMillis: Long) {
        _fechaHora.value = timeInMillis
    }

    fun crearTarea(
        titulo: String,
        descripcion: String,
        imagenPath: String?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Obtener el userId del usuario autenticado
                val userId = autenticacion.getCurrentUserId()
                    ?: throw Exception("No se pudo obtener el ID del usuario. Intente iniciar sesión nuevamente.")

                val fecha = _fechaHora.value?.let { Date(it) } ?: Date() // Usa la fecha seleccionada o la actual
                repository.agregarTarea(
                    titulo = titulo,
                    descripcion = descripcion,
                    fecha = fecha,
                    imagenPath = imagenPath,
                    userId = userId
                )
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}

