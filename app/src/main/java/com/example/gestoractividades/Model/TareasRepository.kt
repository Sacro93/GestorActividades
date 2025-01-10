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
)

/*
Repositorio (TareasRepository):

Realiza las operaciones CRUD directas.
No maneja estados ni lógica específica para casos de uso.Repositorio (TareasRepository):

Realiza las operaciones CRUD directas.
No maneja estados ni lógica específica para casos de uso.*/

class TareasRepository(
    private val contexto: Context,
    private val firestore: FirebaseFirestore) {

    // Guardar imagen en memoria local y devolver la ruta del archivo
    private fun guardarImagenLocal(imagen: Bitmap, nombre: String): String {
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
        imagen: Bitmap?
    ) {
        val tareaId =
            firestore.collection("Tasks").document().id // Generar un ID único para la tarea

        val tareaData = mutableMapOf(
            "id" to tareaId,
            "titulo" to titulo,
            "descripcion" to descripcion,
            "completada" to false,
            "fecha" to Timestamp(fecha) // Guardar la fecha como Timestamp en Firestore
        )

        // Si la imagen no es nula, guardarla localmente y agregar su ruta a Firestore
        if (imagen != null) {
            val rutaImagen = guardarImagenLocal(imagen, tareaId)
            tareaData["imagenPath"] = rutaImagen
        }

        // Subir la tarea a Firestore
        firestore.collection("Tasks").document(tareaId).set(tareaData).await()
    }


suspend fun marcarTareaComoRealizada(id: String) {
    try {
        firestore.collection("Tasks").document(id).update("completada", true).await()
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

    fun obtenerTareas(onSuccess: (List<Tarea>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("Tasks")
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
                        imagenPath = imagenPath
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

                Tarea(
                    id = id,
                    titulo = titulo,
                    descripcion = descripcion,
                    completada = completada,
                    fechaHora = fecha.time,
                    imagenPath = imagenPath
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
