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

    fun crearPintura(titulo: String, descripcion: String, autor: String, nombreArchivoImagen: String, nombreArchivoAudio: String) {
        val pinturasRef = db.collection("pinturas")

        // Obtener URL de la imagen desde Firebase Storage
        obtenerURL("Imagenes/$nombreArchivoImagen")
            .addOnSuccessListener { imagenUri ->
                val imagenURL = imagenUri.toString()

                // Obtener URL del audio desde Firebase Storage
                obtenerURL("Audios/$nombreArchivoAudio")
                    .addOnSuccessListener { audioUri ->
                        val audioURL = audioUri.toString()

                        // Crear un documento con los datos de la pintura incluyendo las URLs
                        val data = hashMapOf(
                            "titulo" to titulo,
                            "descripcion" to descripcion,
                            "autor" to autor,
                            "imagenURL" to imagenURL,
                            "audioURL" to audioURL
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
                        Log.e(TAG, "Error al obtener URL del audio", exception)
                    }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error al obtener URL de la imagen", exception)
            }
    }

    private fun obtenerURL(rutaArchivo: String): Task<Uri> {
        val storageRef = storage.reference.child(rutaArchivo)
        return storageRef.downloadUrl
    }

    fun obtenerPinturas(
        onSuccess: (List<PaintingData>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val pinturasRef = db.collection("pinturas")

        pinturasRef.get()
            .addOnSuccessListener { result ->
                val listaPinturas = mutableListOf<PaintingData>()
                for (document in result) {
                    val name = document.getString("titulo") ?: ""
                    val author = document.getString("autor") ?: ""
                    val description = document.getString("descripcion") ?: ""
                    val imageUrl = document.getString("imagenURL") ?: ""
                    val audioUrl = document.getString("audioURL") ?: ""

                    val paintingData = PaintingData(name, author, description, imageUrl, audioUrl)
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
    val imageUrl: String?, // URL de la imagen
    val audioUrl: String? // URL del audio
)