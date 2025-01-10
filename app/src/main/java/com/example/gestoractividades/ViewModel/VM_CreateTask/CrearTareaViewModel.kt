package com.example.gestoractividades.ViewModel.VM_CreateTask

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestoractividades.Model.TareasRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date



class CrearTareaViewModel(private val repository: TareasRepository) : ViewModel() {

    private val _fechaHora = MutableStateFlow<Long?>(null)
    val fechaHora: StateFlow<Long?> = _fechaHora.asStateFlow()

    fun updateFechaHora(timeInMillis: Long) {
        _fechaHora.value = timeInMillis
    }

    fun crearTarea(
        titulo: String,
        descripcion: String,
        imagen: Bitmap?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val fecha = _fechaHora.value?.let { Date(it) } ?: Date() // Usa la fecha seleccionada o la actual
                repository.agregarTarea(
                    titulo = titulo,
                    descripcion = descripcion,
                    fecha = fecha,
                    imagen = imagen
                )
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}

