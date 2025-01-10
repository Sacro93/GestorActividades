package com.example.gestoractividades.ViewModel.VM_Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestoractividades.Model.TareasRepository
import com.example.gestoractividades.ViewModel.VM_Session.SessionActivaVM

class HomeViewModelFactory(
    private val sessionActivaVM: SessionActivaVM
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel( sessionActivaVM) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}