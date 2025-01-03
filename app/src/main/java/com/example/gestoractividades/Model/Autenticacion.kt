package com.example.gestoractividades.Model

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/*Separación de responsabilidades:

El modelo solo se ocupa de interactuar con FirebaseAuth.
Los ViewModels son responsables de manejar los estados y las validaciones antes de llamar a este modelo.
Manejo de errores:

Devuelve un Result<Unit> para indicar si la operación fue exitosa o falló, lo que facilita el manejo de errores en los ViewModels.*/

class Autenticacion(private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()) {

    // Función para registrar un usuario
    suspend fun registerUser(email: String, password: String): Result<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success("Registro exitoso") // Registro exitoso
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para iniciar sesión
    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit) // Inicio de sesión exitoso
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //futuro
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para obtener el usuario actualmente autenticado
    fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.email
    }

    //Función para cerrar sesión
    fun logoutUser() {
        firebaseAuth.signOut()
    }

}