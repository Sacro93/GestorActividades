package com.example.gestoractividades.ViewModel.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestoractividades.Model.Autenticacion
import com.example.gestoractividades.View.HomeScreen

import com.example.gestoractividades.View.ListadoTareas
import com.example.gestoractividades.View.LoginScreen
import com.example.gestoractividades.View.RegisterScreen
import com.example.gestoractividades.ViewModel.SessionVM.SessionActivaVM
import com.example.gestoractividades.ViewModel.SessionVM.SessionActivaVMFactory

@Composable
fun NavigationWrapper(navController: NavHostController) {


    //Debo instanciar el VM para verificar sesion activa
    //esto se podria hacer en un composable aparte llamado MainScreen por ejemplo, para manejar que la sesion este activa o no
    val sessionActivaVM: SessionActivaVM = viewModel(
        factory = SessionActivaVMFactory(Autenticacion())
    )
    val userEmail by sessionActivaVM.userEmail.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        sessionActivaVM.fetchCurrentUser()

        if (userEmail != null) {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) { inclusive = true }
            }
        } else {
            navController.navigate(Routes.Login.route) {
                popUpTo(Routes.Login.route) { inclusive = true }
            }

        }
    }

    NavHost(
        navController = navController,
        startDestination = if (userEmail != null) Routes.Home.route else Routes.Login.route
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
            val sessionActivaVM: SessionActivaVM = viewModel(factory = SessionActivaVMFactory(Autenticacion()))

            HomeScreen(
                onNavigateToCreateTask = { navController.navigate(Routes.CreateTask.route) },
                onNavigateToListTasks = { navController.navigate(Routes.ListadoTareas.route) },
                sessionActivaVM = sessionActivaVM,//viewModel
                logoutUser = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                }
            )
        }


        composable(Routes.ListadoTareas.route) {
            ListadoTareas(
                onBack = { navController.navigateUp() },
                onCreateTask = { navController.navigate(Routes.CreateTask.route) })

        }
    }
}