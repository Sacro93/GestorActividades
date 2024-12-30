package com.example.gestoractividades.View

import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color

@Composable
fun ConfigureSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true // Cambiar a 'false' si usas un fondo oscuro

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White, // Color de las barras
            darkIcons = useDarkIcons // Íconos oscuros o claros
        )
    }
}
@Composable
fun HideSystemBars() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.isSystemBarsVisible = false // Oculta ambas barras
    }
}
@Composable
fun ShowSystemBars() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.isSystemBarsVisible = true // Muestra ambas barras
    }
}
