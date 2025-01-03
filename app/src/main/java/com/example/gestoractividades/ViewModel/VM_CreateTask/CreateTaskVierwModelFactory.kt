package com.example.gestoractividades.ViewModel.VM_CreateTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestoractividades.Model.TaskManager

class CreateTaskViewModelFactory(private val taskManager: TaskManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateTaskViewModel(taskManager) as T
        }
        throw IllegalArgumentException("Clase desconocida")
    }
}