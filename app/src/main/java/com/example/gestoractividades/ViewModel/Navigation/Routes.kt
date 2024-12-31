package com.example.gestoractividades.ViewModel.Navigation


sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object CreateTask : Routes("createTask")
    object ListadoTareas : Routes("listadoTareas")
}

