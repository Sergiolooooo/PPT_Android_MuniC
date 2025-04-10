package com.example.ppt_munic.pantallas.incidencia

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.ppt_munic.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class IncidenciaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCedula: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etIdIncidencia: EditText
    private lateinit var etProvincia: EditText
    private lateinit var etCanton: EditText
    private lateinit var etDistrito: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnEnviar: Button
    private lateinit var imgPreview: ImageView

    private var imagenSeleccionada: File? = null
    private val PICK_IMAGE_REQUEST = 101
    private val TAKE_PHOTO_REQUEST = 102
    private var currentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_incidencia)

        etNombre = findViewById(R.id.etNombre)
        etCedula = findViewById(R.id.etCedula)
        etTelefono = findViewById(R.id.etTelefono)
        etIdIncidencia = findViewById(R.id.etIdIncidencia)
        etProvincia = findViewById(R.id.etProvincia)
        etCanton = findViewById(R.id.etCanton)
        etDistrito = findViewById(R.id.etDistrito)
        etDireccion = findViewById(R.id.etDireccion)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnEnviar = findViewById(R.id.btnEnviar)
        imgPreview = findViewById(R.id.imgPreview)

        btnSeleccionarImagen.setOnClickListener {
            mostrarOpcionesImagen()
        }

        btnEnviar.setOnClickListener {
            enviarIncidencia()
        }
    }

    private fun mostrarOpcionesImagen() {
        val opciones = arrayOf("Galería", "Cámara")
        AlertDialog.Builder(this)
            .setTitle("Seleccionar imagen desde:")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> abrirGaleria()
                    1 -> abrirCamara()
                }
            }
            .show()
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), PICK_IMAGE_REQUEST)
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = try {
            crearArchivoTemporal()
        } catch (ex: IOException) {
            null
        }

        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.ppt_munic.fileprovider",
                it
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }
    }

    private fun crearArchivoTemporal(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    data?.data?.let { uri ->
                        imagenSeleccionada = copiarImagenTemporal(uri)
                        imagenSeleccionada?.let {
                            imgPreview.setImageURI(Uri.fromFile(it))
                            Toast.makeText(this, "Imagen seleccionada desde galería", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                TAKE_PHOTO_REQUEST -> {
                    imagenSeleccionada = File(currentPhotoPath)
                    if (imagenSeleccionada?.exists() == true) {
                        imgPreview.setImageURI(Uri.fromFile(imagenSeleccionada))
                        Toast.makeText(this, "Foto tomada con cámara", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun copiarImagenTemporal(uri: Uri): File? {
        return try {
            val returnCursor = contentResolver.query(uri, null, null, null, null) ?: return null
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            val name = returnCursor.getString(nameIndex)
            returnCursor.close()

            val file = File(cacheDir, name)
            contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun enviarIncidencia() {
        val nombre = etNombre.text.toString()
        val cedula = etCedula.text.toString().toIntOrNull()
        val telefono = etTelefono.text.toString().toIntOrNull()
        val idIncidencia = etIdIncidencia.text.toString().toIntOrNull()
        val provincia = etProvincia.text.toString()
        val canton = etCanton.text.toString()
        val distrito = etDistrito.text.toString()
        val direccion = etDireccion.text.toString()
        val imagen = imagenSeleccionada

        if (nombre.isBlank() || cedula == null || telefono == null || idIncidencia == null ||
            provincia.isBlank() || canton.isBlank() || distrito.isBlank() || direccion.isBlank() || imagen == null) {
            Toast.makeText(this, "Completa todos los campos y selecciona una imagen", Toast.LENGTH_LONG).show()
            return
        }

        EnviarIncidencia().enviar(
            nombre = nombre,
            cedula = cedula,
            telefono = telefono,
            idIncidencia = idIncidencia,
            provincia = provincia,
            canton = canton,
            distrito = distrito,
            direccion = direccion,
            imagenFile = imagen,
            onSuccess = { mensaje ->
                Toast.makeText(this, "✅ $mensaje", Toast.LENGTH_LONG).show()
                finish()
            },
            onError = { error ->
                Toast.makeText(this, "❌ $error", Toast.LENGTH_LONG).show()
            }
        )
    }
}
