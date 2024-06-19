package com.example.museo.data
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class FirebaseManager {

    private val db = FirebaseFirestore.getInstance()
    private val storage = Firebase.storage

    fun crearPintura(titulo: String, descripcion: String, autor: String, nombreArchivo: String) {
        // Referencia a la colección "pinturas" en Firestore
        val pinturasRef = db.collection("pinturas")

        // Obtener la URL de la imagen desde Firebase Storage
        obtenerURLImagen(nombreArchivo)
            .addOnSuccessListener { uri ->
                val imagenURL = uri.toString()

                // Crear un documento con los datos de la pintura incluyendo la URL de la imagen
                val data = hashMapOf(
                    "titulo" to titulo,
                    "descripcion" to descripcion,
                    "autor" to autor,
                    "imagenURL" to imagenURL
                )

                // Guardar el documento en Firestore
                pinturasRef.add(data)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "Documento agregado con ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error al agregar documento", e)
                    }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error al obtener URL de la imagen", exception)
            }
    }

    private fun obtenerURLImagen(nombreArchivo: String): Task<Uri> {
        // Referencia al archivo en Firebase Storage
        val storageRef = storage.reference.child("Imagenes/$nombreArchivo")

        // Obtener la URL del archivo
        return storageRef.downloadUrl
    }

    fun obtenerPinturas(
        onSuccess: (List<PaintingData>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Referencia a la colección "pinturas" en Firestore
        val pinturasRef = db.collection("pinturas")

        // Obtener todas las pinturas desde Firestore
        pinturasRef.get()
            .addOnSuccessListener { result ->
                val listaPinturas = mutableListOf<PaintingData>()
                for (document in result) {
                    val name = document.getString("titulo") ?: ""
                    val author = document.getString("autor") ?: ""
                    val description = document.getString("descripcion") ?: ""
                    val imageUrl = document.getString("imagenURL") ?: ""

                    val paintingData = PaintingData(name, author, description, imageUrl)
                    listaPinturas.add(paintingData)
                }
                onSuccess(listaPinturas)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    companion object {
        private const val TAG = "FirebaseManager"
    }
}

data class PaintingData(
    val name: String,
    val author: String,
    val description: String,
    val imageUrl: String? // URL de la imagen
)
