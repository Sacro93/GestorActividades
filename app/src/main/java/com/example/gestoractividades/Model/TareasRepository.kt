package com.example.gestoractividades.Model


import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.util.Date

data class Tarea(
    val id: String = "", // Se completará al obtener el ID generado en Firestore
    val titulo: String = "",
    val descripcion: String = "",
    val completada: Boolean = false,
    val imagenPath: String? = null, // Ruta de la imagen en memoria
    val fechaHora: Long = 0L,
    val userId :String
)

/*
Repositorio (TareasRepository):

Realiza las operaciones CRUD directas.
No maneja estados ni lógica específica para casos de uso.*/

class TareasRepository(
    private val contexto: Context,
    private val firestore: FirebaseFirestore) {

    // Guardar imagen en memoria local y devolver la ruta del archivo
    fun guardarImagenLocal(imagen: Bitmap, nombre: String): String {
        val archivo = File(contexto.filesDir, "$nombre.jpg") // Guardar en almacenamiento interno
        FileOutputStream(archivo).use { fos ->
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
        return archivo.absolutePath // Devolver la ruta completa de la imagen
    }

    // Agregar tarea a Firestore
    suspend fun agregarTarea(
        titulo: String,
        descripcion: String,
        fecha: Date,
        imagenPath: String?,
        userId: String
    ) {
        val tareaId =
            firestore.collection("Tasks")
                .document()
                .id // Generar un ID único para la tarea

        val tareaData = mutableMapOf(
            "id" to tareaId,
            "titulo" to titulo,
            "descripcion" to descripcion,
            "completada" to false,
            "fecha" to Timestamp(fecha),// Guardar la fecha como Timestamp en Firestore
            "userId" to userId
        )
        // Si la imagen no es nula, guardarla localmente y agregar su ruta a Firestore
        if (imagenPath  != null) {
            tareaData["imagenPath"] = imagenPath
        }

        // Subir la tarea a Firestore
        firestore.collection("Tasks").document(tareaId).set(tareaData).await()
    }


suspend fun marcarTareaComoRealizada(id: String) {
    try {
        firestore.collection("Tasks")
            .document(id)
            .update("completada", true)
            .await()
    } catch (e: Exception) {
        throw Exception("Error al marcar la tarea como realizada: ${e.message}")
    }
}


suspend fun eliminarTarea(id: String) {
        val documento = firestore.collection("Tasks").document(id).get().await()
        if (documento.exists()) {
            val imagenPath = documento.getString("imagenPath")
            if (!imagenPath.isNullOrEmpty()) {
                val archivo = File(imagenPath)
                if (archivo.exists()) {
                    archivo.delete()
                }
            }
        }
        firestore.collection("Tasks").document(id).delete().await()
    }

    fun obtenerTareas(
        userId: String, // Filtro por usuario
        onSuccess: (List<Tarea>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("Tasks")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val tareas = snapshot.documents.mapNotNull { doc ->
                    val id = doc.getString("id") ?: return@mapNotNull null
                    val titulo = doc.getString("titulo") ?: return@mapNotNull null
                    val descripcion = doc.getString("descripcion") ?: return@mapNotNull null
                    val completada = doc.getBoolean("completada") ?: false
                    val fecha = doc.getTimestamp("fecha")?.toDate() ?: Date()
                    val imagenPath = doc.getString("imagenPath")
                    Tarea(
                        id = id,
                        titulo = titulo,
                        descripcion = descripcion,
                        completada = completada,
                        fechaHora = fecha.time,
                        imagenPath = imagenPath,
                        userId = userId
                    )
                }
                onSuccess(tareas)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    suspend fun obtenerTareaPorId(id: String): Tarea? {
        return try {
            val documento = firestore.collection("Tasks").document(id).get().await()
            if (documento.exists()) {
                val titulo = documento.getString("titulo") ?: ""
                val descripcion = documento.getString("descripcion") ?: ""
                val completada = documento.getBoolean("completada") ?: false
                val fecha = documento.getTimestamp("fecha")?.toDate() ?: Date()
                val imagenPath = documento.getString("imagenPath")
                val userId = documento.getString("userId") ?: "" // Obtener el userId

                Tarea(
                    id = id,
                    titulo = titulo,
                    descripcion = descripcion,
                    completada = completada,
                    fechaHora = fecha.time,
                    imagenPath = imagenPath,
                    userId = userId // Asignar el userId al modelo
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}

