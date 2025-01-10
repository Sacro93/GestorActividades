package com.example.gestoractividades.View

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.gestoractividades.ViewModel.Navigation.Routes
import com.example.gestoractividades.ViewModel.VM_Session.SessionActivaVM
import kotlinx.coroutines.delay
/*
@Composable
fun PantallaPrevia(navController: NavHostController, sessionActivaVM: SessionActivaVM) {
    LaunchedEffect(Unit) {
        sessionActivaVM.fetchCurrentUser()

        // Retrasar un poco para mostrar el SplashScreen
        delay(2000)

        if (sessionActivaVM.userEmail.value != null) {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Previa.route) { inclusive = true }
            }
        } else {
            navController.navigate(Routes.Login.route) {
                popUpTo(Routes.Previa.route) { inclusive = true }
            }
        }
    }

    // UI del SplashScreen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Cargando...")
    }
}

*/
