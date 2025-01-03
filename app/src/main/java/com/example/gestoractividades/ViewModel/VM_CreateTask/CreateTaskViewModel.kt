package com.example.gestoractividades.ViewModel.VM_CreateTask

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestoractividades.Model.Task
import com.example.gestoractividades.Model.TaskManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


sealed class CreateTaskState {
    data object Idle : CreateTaskState()
    data object Loading : CreateTaskState()
    data object Success : CreateTaskState()
    data class Error(val message: String) : CreateTaskState()
}

class CreateTaskViewModel(
    private val taskManager: TaskManager
) : ViewModel() {


    /*Manejan el estado de la creación de tareas (Idle, Loading, Success, Error)*/
    private val _createTaskState = MutableStateFlow<CreateTaskState>(CreateTaskState.Idle)
    val createTaskState: StateFlow<CreateTaskState> = _createTaskState.asStateFlow()


    // Estados específicos para los campos
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion.asStateFlow()

    private val _fechaHora = MutableStateFlow("")
    val fechaHora: StateFlow<String> = _fechaHora.asStateFlow()

    private val _selectedImage =
        MutableStateFlow<String?>(null) // La imagen será la URI de la imagen capturada
    val selectedImage: StateFlow<String?> = _selectedImage.asStateFlow()


    // Funciones para actualizar estados específicos
    fun updateNombre(value: String) {
        _nombre.value = value
    }

    fun updateDescripcion(value: String) {
        _descripcion.value = value
    }

    fun updateFechaHora(value: String) {
        _fechaHora.value = value
    }

    fun updateSelectedImage(uri: String) {
        _selectedImage.value = uri
    }

    /*La función saveBitmapToInternalStorage se encarga de:

    Tomar un Bitmap capturado desde la cámara.
    Guardar ese Bitmap en el almacenamiento interno del dispositivo como un archivo físico.
    Devolver la URI del archivo creado.*/
    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): Uri? {
        return try {
            val filename = "captured_image_${System.currentTimeMillis()}.png"
            val file = File(context.cacheDir, filename)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            file.toUri()
        } catch (e: Exception) {
            null //
        }
    }

    // Función para crear una nueva tarea
    fun createTask() {
        if (_nombre.value.isBlank() || _descripcion.value.isBlank() || _fechaHora.value.isBlank()) {
            _createTaskState.value = CreateTaskState.Error("Todos los campos son obligatorios")
            return
        }

        _createTaskState.value = CreateTaskState.Loading

        val task = Task(
            nombre = _nombre.value,
            descripcion = _descripcion.value,
            fechaHora = System.currentTimeMillis(),
            imagen = _selectedImage.value
        )

        viewModelScope.launch {
            try {
                val result = taskManager.saveTask(task)
                result.onSuccess {
                    _createTaskState.value = CreateTaskState.Success
                }.onFailure { e ->
                    _createTaskState.value =
                        CreateTaskState.Error(e.localizedMessage ?: "Error al guardar la tarea")
                }
            } catch (e: Exception) {
                _createTaskState.value =
                    CreateTaskState.Error(e.localizedMessage ?: "Error inesperado")
            }
        }
    }
}
