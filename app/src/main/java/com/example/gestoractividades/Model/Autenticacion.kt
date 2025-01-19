package com.example.gestoractividades.Model

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/*Separación de responsabilidades:

El modelo solo se ocupa de interactuar con FirebaseAuth.
Los ViewModels son responsables de manejar los estados y las validaciones antes de llamar a este modelo.
Manejo de errores:

Devuelve un Result<Unit> para indicar si la operación fue exitosa o falló, lo que facilita el manejo de errores en los ViewModels.*/

class Autenticacion(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Función que maneja el registro completo del usuario (Firebase Auth + Firestore)
    suspend fun registerUser(email: String, password: String, username: String): Result<Unit> {
        return try {
            // Registrar al usuario en FirebaseAuth
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid
                ?: return Result.failure(Exception("Error: No se pudo obtener el ID del usuario."))

            // Guardar datos adicionales del usuario en Firestore
            val saveResult = saveUserDetails(userId, username, email)
            if (saveResult.isSuccess) {
                Result.success(Unit) // Éxito
            } else {
                Result.failure(
                    saveResult.exceptionOrNull()
                        ?: Exception("Error desconocido al guardar en Firestore")
                )
            }
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

    // Función específica para guardar datos del usuario en Firestore
    private suspend fun saveUserDetails(
        userId: String,
        username: String,
        email: String
    ): Result<Unit> {
        return try {
            val userData = mapOf(
                "id" to userId, //omitir o borrar
                "username" to username,
                "email" to email,
                "createdAt" to Timestamp.now(),
                "profileImageUrl" to "" // borrar
            )
            firestore.collection("Users").document(userId).set(userData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    //Método para obtener detalles del usuario desde Firestore

    suspend fun getUserDetails(userId: String): Result<Map<String, Any>> {
        return try {
            val snapshot = firestore.collection("Users")
                .document(userId)
                .get()
                .await()
            if (snapshot.exists()) {
                Result.success(snapshot.data ?: emptyMap())
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Función para obtener el usuario actualmente autenticado
    fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    //Función para cerrar sesión
    fun logoutUser() {
        firebaseAuth.signOut()
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


}