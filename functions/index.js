/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

// const {onRequest} = require("firebase-functions/v2/https");
// const logger = require("firebase-functions/logger");

// Create and deploy your first functionSs
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
const functions = require("firebase-functions");
const admin = require("firebase-admin");
const express = require("express");

const app = express();

// Inicializa la app de Firebase
admin.initializeApp({
  credential: admin.credential.cert("./permisos.json"),
  storageBucket: "pinturas-8bf11.appspot.com", // Nombre del bucket
});

// Inicializa Firestore
const db = admin.firestore();
const storage = admin.storage().bucket();

// Crear una pintura
app.post("/api/pinturas", async (req, res) => {
  const {
    titulo,
    descripcion,
    autor,
    nombreArchivoImagen,
    nombreArchivoAudio,
    salonID,
  } = req.body;

  try {
    // Validar que los nombres de archivos no sean undefined
    if (!nombreArchivoImagen || !nombreArchivoAudio) {
      return res.status(400).json({error: "Faltan nombres de archivo."});
    }

    // Obtener URL de la imagen desde Firebase Storage
    const imagenURL = await obtenerURL(`Imagenes/${nombreArchivoImagen}`);

    // Obtener URL del audio desde Firebase Storage
    const audioURL = await obtenerURL(`Audios/${nombreArchivoAudio}`);

    // Crear un documento con los datos de la pintura incluyendo las URLs
    const data = {
      titulo,
      descripcion,
      autor,
      imagenURL,
      audioURL,
      salonID,
    };

    // Guardar el documento en Firestore
    const documentReference = await db.collection("pinturas").add(data);
    console.log("Documento agregado con ID:", documentReference.id);
    return res.status(200).json({id: documentReference.id});
  } catch (error) {
    console.error("Error al agregar documento:", error);
    return res.status(500).json({error: "Error al agregar documento"});
  }
});

/**
 * Obtiene una URL firmada para un archivo en Firebase Storage.
 * @param {string} rutaArchivo - La ruta del archivo en Firebase Storage.
 * @return {Promise<string>} - La URL firmada del archivo.
 */
async function obtenerURL(rutaArchivo) {
  try {
    const file = storage.file(rutaArchivo);
    const [url] = await file.getSignedUrl({
      action: "read",
      expires: "03-09-2025", // Puedes ajustar esta fecha según tus necesidades
    });
    return url;
  } catch (error) {
    console.error("Error al obtener la URL firmada:", error);
    throw new Error("No se pudo obtener la URL firmada.");
  }
}

// Leer todas las pinturas
app.get("/api/pinturas", async (req, res) => {
  try {
    // Obtener la referencia a la colección
    const query = db.collection("pinturas");

    // Obtener todos los documentos en la colección
    const querySnapshot = await query.get();
    const docs = querySnapshot.docs;

    // Mapear los documentos a un array de objetos
    const response = docs.map((doc) => ({
      id: doc.id,
      titulo: doc.data().titulo,
      autor: doc.data().autor,
      descripcion: doc.data().descripcion,
      imagenURL: doc.data().imagenURL,
      audioURL: doc.data().audioURL,
      salonID: doc.data().salonID,
    }));

    // Enviar la respuesta como JSON
    return res.status(200).json(response);
  } catch (error) {
    // Manejar errores
    console.error("Error al obtener las pinturas:", error);
    return res.status(500).json({error: "Error al obtener las pinturas"});
  }
});

// Actualizar una pintura
app.put("/api/pinturas/:id", async (req, res) => {
  const {id} = req.params;
  const {
    titulo,
    descripcion,
    autor,
    nombreArchivoImagen,
    nombreArchivoAudio,
    salonID,
  } = req.body;

  try {
    const pinturaRef = db.collection("pinturas").doc(id);
    const pinturaDoc = await pinturaRef.get();

    if (!pinturaDoc.exists) {
      return res.status(404).json({error: "Pintura no encontrada"});
    }

    const data = {};

    if (titulo) data.titulo = titulo;
    if (descripcion) data.descripcion = descripcion;
    if (autor) data.autor = autor;
    if (salonID) data.salonID = salonID;

    if (nombreArchivoImagen) {
      const imagenURL = await obtenerURL(`Imagenes/${nombreArchivoImagen}`);
      data.imagenURL = imagenURL;
    }

    if (nombreArchivoAudio) {
      const audioURL = await obtenerURL(`Audios/${nombreArchivoAudio}`);
      data.audioURL = audioURL;
    }

    await pinturaRef.update(data);

    return res.status(200).json({message: "Pintura actualizada correctamente"});
  } catch (error) {
    console.error("Error al actualizar la pintura:", error);
    return res.status(500).json({error: "Error al actualizar la pintura"});
  }
});

// Exportar Functions
exports.app = functions.https.onRequest(app);
