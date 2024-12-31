package com.example.gestoractividades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gestoractividades.ui.theme.GestorActividadesTheme
import androidx.navigation.compose.rememberNavController
import com.example.gestoractividades.ViewModel.Navigation.NavigationWrapper


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorActividadesTheme {
                val navController = rememberNavController()
                NavigationWrapper(navController = navController)
            }
        }
    }
}