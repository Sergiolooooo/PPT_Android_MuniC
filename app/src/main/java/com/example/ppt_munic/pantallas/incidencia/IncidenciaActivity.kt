package com.example.ppt_munic.pantallas.incidencia

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class IncidenciaActivity : DrawerActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCedula: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etProvincia: EditText
    private lateinit var etCanton: EditText
    private lateinit var etDistrito: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnEnviar: Button
    private lateinit var imgPreview: ImageView
    private lateinit var titulo: TextView
    private lateinit var btnCerrar: ImageView

    private var imagenSeleccionada: File? = null
    private var currentPhotoPath: String = ""
    private lateinit var photoUri: Uri

    private var idIncidenciaRecibido: Int = -1

    // Launchers
    private lateinit var galeriaLauncher: ActivityResultLauncher<String>
    private lateinit var camaraLauncher: ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_incidencia)

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre)
        etCedula = findViewById(R.id.etCedula)
        etTelefono = findViewById(R.id.etTelefono)
        etProvincia = findViewById(R.id.etProvincia)
        etCanton = findViewById(R.id.etCanton)
        etDistrito = findViewById(R.id.etDistrito)
        etDireccion = findViewById(R.id.etDireccion)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnEnviar = findViewById(R.id.btnEnviar)
        imgPreview = findViewById(R.id.imgPreview)
        titulo = findViewById(R.id.titulo_incidencia)
        btnCerrar = findViewById(R.id.btn_cerrar)
        // Establecer valores por defecto
        etProvincia.setText("Guanacaste")
        etCanton.setText("Cañas")

        // Recuperar datos de la incidencia
        idIncidenciaRecibido = intent.getIntExtra("id_incidencia", -1)
        val nombreIncidencia = intent.getStringExtra("nombre_incidencia")
        titulo.text = nombreIncidencia ?: "Incidencia"

        btnCerrar.setOnClickListener { finish() }
        inicializarLaunchers()

        btnSeleccionarImagen.setOnClickListener {
            mostrarOpcionesImagen()
        }

        btnEnviar.setOnClickListener {
            enviarIncidencia()
        }
    }

    private fun inicializarLaunchers() {
        galeriaLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imagenSeleccionada = copiarImagenTemporal(it)?.let { file -> comprimirImagen(file) }
                imagenSeleccionada?.let { file ->
                    imgPreview.setImageURI(Uri.fromFile(file))
                    Toast.makeText(this, "Imagen seleccionada desde galería", Toast.LENGTH_SHORT).show()
                }
            }
        }

        camaraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imagenSeleccionada = File(currentPhotoPath).let { comprimirImagen(it) }
                if (imagenSeleccionada?.exists() == true) {
                    imgPreview.setImageURI(Uri.fromFile(imagenSeleccionada))
                    Toast.makeText(this, "Foto tomada con cámara", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarOpcionesImagen() {
        val opciones = arrayOf("Galería", "Cámara")
        AlertDialog.Builder(this)
            .setTitle("Seleccionar imagen desde:")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> galeriaLauncher.launch("image/*")
                    1 -> abrirCamara()
                }
            }
            .show()
    }

    private fun abrirCamara() {
        val photoFile = try {
            crearArchivoTemporal()
        } catch (ex: IOException) {
            null
        }

        photoFile?.also {
            photoUri = FileProvider.getUriForFile(
                this,
                "com.example.ppt_munic.fileprovider",
                it
            )
            camaraLauncher.launch(photoUri)
        }
    }

    private fun crearArchivoTemporal(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
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

    private fun comprimirImagen(file: File): File {
        val originalBitmap = BitmapFactory.decodeFile(file.absolutePath)
        val nuevoArchivo = File(cacheDir, "comprimido_${file.nameWithoutExtension}.jpg")
        FileOutputStream(nuevoArchivo).use { out ->
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)
        }
        return nuevoArchivo
    }

    private fun enviarIncidencia() {
        val nombre = etNombre.text.toString()
        val cedula = etCedula.text.toString().toIntOrNull()
        val telefono = etTelefono.text.toString().toIntOrNull()
        val provincia = etProvincia.text.toString()
        val canton = etCanton.text.toString()
        val distrito = etDistrito.text.toString()
        val direccion = etDireccion.text.toString()
        val imagen = imagenSeleccionada

        if (nombre.isBlank() || cedula == null || telefono == null ||
            provincia.isBlank() || canton.isBlank() || distrito.isBlank() || direccion.isBlank() || imagen == null
        ) {
            Toast.makeText(this, "Completa todos los campos y selecciona una imagen", Toast.LENGTH_LONG).show()
            return
        }

        EnviarIncidencia().enviar(
            nombre = nombre,
            cedula = cedula,
            telefono = telefono,
            idIncidencia = idIncidenciaRecibido, // usamos el valor recibido
            provincia = provincia,
            canton = canton,
            distrito = distrito,
            direccion = direccion,
            imagenFile = imagen,
            onSuccess = { mensaje ->
                Toast.makeText(this, " $mensaje", Toast.LENGTH_LONG).show()
                finish()
            },
            onError = { error ->
                Toast.makeText(this, "❌ $error", Toast.LENGTH_LONG).show()
            }
        )
    }
}
