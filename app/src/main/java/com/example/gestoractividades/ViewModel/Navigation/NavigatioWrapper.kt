package com.example.gestoractividades.ViewModel.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestoractividades.Model.Autenticacion
import com.example.gestoractividades.Model.TareasRepository
import com.example.gestoractividades.View.CrearTarea
import com.example.gestoractividades.View.HomeScreen
import com.example.gestoractividades.View.ListadoTareas
import com.example.gestoractividades.View.LoginScreen
import com.example.gestoractividades.View.RegisterScreen
import com.example.gestoractividades.ViewModel.VM_CreateTask.CrearTareaViewModel
import com.example.gestoractividades.ViewModel.VM_CreateTask.CrearTareaViewModelFactory
import com.example.gestoractividades.ViewModel.VM_Home.HomeViewModel
import com.example.gestoractividades.ViewModel.VM_Home.HomeViewModelFactory
import com.example.gestoractividades.ViewModel.VM_ListadoTareas.ListadoTareasViewModel
import com.example.gestoractividades.ViewModel.VM_ListadoTareas.ListadoTareasViewModelFactory
import com.example.gestoractividades.ViewModel.VM_Session.SessionActivaVM
import com.example.gestoractividades.ViewModel.VM_Session.SessionActivaVMFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun NavigationWrapper(navController: NavHostController) {

    val sessionActivaVM: SessionActivaVM = viewModel(
        factory = SessionActivaVMFactory(Autenticacion())
    )

    // Llama a fetchCurrentUser al iniciar
    LaunchedEffect(Unit) {
        sessionActivaVM.fetchCurrentUser()
    }

    val userEmail by sessionActivaVM.userEmail.collectAsStateWithLifecycle()



    val context = LocalContext.current
    val tareasRepository = remember {
        TareasRepository(
            contexto = context,
            firestore = FirebaseFirestore.getInstance()
        )
    }

    // Redirigir a Home o Login según el estado de la sesión
    LaunchedEffect(userEmail) {
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

    // Configurar la navegación
    NavHost(
        navController = navController,
        startDestination = if (userEmail != null) Routes.Home.route else Routes.Login.route
    ) {
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
            // Usa la instancia única de SessionActivaVM
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(sessionActivaVM)
            )

            HomeScreen(
                onNavigateToCreateTask = { navController.navigate(Routes.CrearTarea.route) },
                onNavigateToListTasks = { navController.navigate(Routes.ListadoTareas.route) },
                homeViewModel = homeViewModel,
                logoutUser = {
                    sessionActivaVM.cerrarSesion()
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                },
                sessionActivaVM = sessionActivaVM // Usa la misma instancia
            )
        }



        composable(Routes.ListadoTareas.route) {
            val listadoTareasViewModel: ListadoTareasViewModel = viewModel(
                factory = ListadoTareasViewModelFactory(
                    tareasRepository = tareasRepository,
                    autenticacion = Autenticacion(
                        firebaseAuth = FirebaseAuth.getInstance(),
                        firestore = FirebaseFirestore.getInstance()
                    )
                )
            )

            ListadoTareas(
                viewModel = listadoTareasViewModel,
                onBack = { navController.navigateUp() }
            )
        }


        composable(Routes.CrearTarea.route) {
            val crearTareaViewModel: CrearTareaViewModel = viewModel(
                factory = CrearTareaViewModelFactory(
                    TareasRepository(
                        contexto = LocalContext.current,
                        firestore = FirebaseFirestore.getInstance()
                    ),
                    Autenticacion(
                        firebaseAuth = FirebaseAuth.getInstance(),
                        firestore = FirebaseFirestore.getInstance()
                    )
                )
            )

            CrearTarea(
                viewModel = crearTareaViewModel,
                        onBack = { navController.navigateUp() }
            )
        }
    }
}

