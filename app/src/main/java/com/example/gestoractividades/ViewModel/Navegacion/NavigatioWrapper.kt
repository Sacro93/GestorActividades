package com.example.gestoractividades.ViewModel.Navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestoractividades.View.HomeScreen
import com.example.gestoractividades.EjemplosView.RegisterScreen
import com.example.gestoractividades.View.ListadoTareas
import com.example.gestoractividades.View.LoginScreen

@Composable
fun NavigationWrapper(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    )// Define la pantalla inicial
    {
        composable(Routes.Login.route) {
            LoginScreen(
                onLogin = { navController.navigate(Routes.Home.route) },
                onRegister = { navController.navigate(Routes.Register.route) }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onRegister = { navController.navigate(Routes.Home.route) },
                onBack = { navController.navigateUp() }
            )
        }
        composable(Routes.Home.route) {
            HomeScreen(
                onNavigateToCreateTask = { navController.navigate(Routes.CreateTask.route) },
                onNavigateToListTasks = { navController.navigate(Routes.ListadoTareas.route) }
            )
        }

        composable(Routes.ListadoTareas.route) {
            ListadoTareas(
                onBack = { navController.navigateUp() },
                onCreateTask = { navController.navigate(Routes.CreateTask.route) })

        }
    }
}