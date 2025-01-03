package com.example.gestoractividades.Model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class Task(
    val nombre: String,
    val descripcion: String,
    val fechaHora: Long,
    val imagen: String? = null
    /*La URL de la imagen se guarda como un string.
    Si necesitas mostrar imágenes, utiliza esa URL con Image de Jetpack Compose o alguna librería como Coil para cargarla.*/
)

class TaskManager(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val collectionName = "Task"

    suspend fun saveTask(task: Task): Result<String> {
        return try {
            val documentReference = firestore.collection(collectionName)
                .add(task)
                .await()
            Result.success(documentReference.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener todas las tareas
    suspend fun getTasks(): Result<List<Task>> {
        return try {
            val snapshot = firestore.collection(collectionName).get().await()
            val tasks = snapshot.documents.mapNotNull { it.toObject(Task::class.java) }
            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            firestore.collection(collectionName).document(taskId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
/*

FIRESTONE


*rules_version = '2';

service cloud.firestore {
match /databases/{database}/documents {
match /{document=**} {
  allow read, write: if true;
}
}
}
*/

