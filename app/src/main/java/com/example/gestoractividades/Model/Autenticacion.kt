package com.example.gestoractividades.Model

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await


class Autenticacion (private val firebaseAuth: FirebaseAuth= FirebaseAuth.getInstance()){

    // Función para registrar un usuario
    suspend fun registerUser(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit) // Registro exitoso
        } catch (e: Exception) {
            Result.failure(e) // Error durante el registro
        }
    }

    // Función para iniciar sesión
    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit) // Inicio de sesión exitoso
        } catch (e: Exception) {
            Result.failure(e) // Error durante el inicio de sesión
        }
    }

    // Función para obtener el usuario actualmente autenticado
    fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.email // Devuelve el correo electrónico del usuario autenticado o null
    }
    // Función para cerrar sesión
    fun logoutUser() {
        firebaseAuth.signOut()
    }

}