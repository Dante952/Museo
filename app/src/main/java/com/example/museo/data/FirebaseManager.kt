package com.example.museo.data

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseManager {

    private val db = FirebaseFirestore.getInstance()

    fun subirDatosPintura(titulo: String, descripcion: String) {
        // Crear un nuevo documento en la colección 'pinturas'
        val pinturasRef = db.collection("pinturas")

        // Crear un mapa con los datos a subir
        val data = hashMapOf(
            "titulo" to titulo,
            "descripcion" to descripcion
        )

        // Agregar el documento a la colección
        pinturasRef.add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Documento agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al agregar documento", e)
            }
    }
    fun obtenerPinturas(onSuccess: (List<PaintingData>) -> Unit, onFailure: (Exception) -> Unit) {
        val pinturasRef = db.collection("pinturas")

        pinturasRef.get()
            .addOnSuccessListener { result ->
                val listaPinturas = mutableListOf<PaintingData>()
                for (document in result) {
                    val name = document.getString("titulo") ?: ""
                    val author = document.getString("autor") ?: ""
                    val description = document.getString("descripcion") ?: ""
                    // Aquí se usa "titulo", "autor" y "descripcion" según como los definiste en Firestore

                    // Asumo que no estás utilizando las URL de imagen y audio por ahora en Firestore
                    val imageUri: Uri? = null
                    val audioUri: Uri? = null

                    val paintingData = PaintingData(name, author, description, imageUri, audioUri)
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
    val imageUri: Uri?, // Uri de la imagen local
    val audioUri: Uri?   // Uri del audio local
)
