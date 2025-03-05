package com.example.ppt_munic.pantallas.comercio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ppt_munic.R

class DetalleComercio : AppCompatActivity() {

    private lateinit var btnCerrar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comercio)

        val iconoComercio: ImageView = findViewById(R.id.iconoComercio)
        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)
        val btnYouTube: ImageView = findViewById(R.id.btnYouTube)
        val btnGoogle: ImageView = findViewById(R.id.btnGoogle) // 🔹 Nuevo botón para abrir Google Maps
        btnCerrar = findViewById(R.id.btn_cerrar)

        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val telefono = intent.getStringExtra("telefono")
        val videoUrl = intent.getStringExtra("video_youtube") ?: "Sin video"
        val urlGoogle = intent.getStringExtra("url_google") ?: "Sin enlace" // 🔹 Captura la URL de Google
        val iconoRes = intent.getIntExtra("iconoCategoria", R.drawable.ic_default)

        // Asignar valores a los TextView
        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        tvTelefono.text = if (!telefono.isNullOrEmpty() && telefono != "null") "Teléfono: $telefono" else "Teléfono: No disponible"

        // Cargar icono de la categoría seleccionado
        Glide.with(this)
            .load(iconoRes)
            .placeholder(R.drawable.ic_default)
            .into(iconoComercio)

        // Cerrar la actividad cuando se presiona el botón de cerrar
        btnCerrar.setOnClickListener {
            finish()
        }

        // 🔹 Validar si la URL de YouTube es válida antes de mostrar el icono
        if (isValidYouTubeUrl(videoUrl)) {
            btnYouTube.visibility = View.VISIBLE
            btnYouTube.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }
        } else {
            btnYouTube.visibility = View.GONE
        }

        // 🔹 Validar si la URL de Google es válida antes de mostrar el icono
        if (isValidGoogleUrl(urlGoogle)) {
            btnGoogle.visibility = View.VISIBLE
            btnGoogle.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlGoogle))
                startActivity(intent)
            }
        } else {
            btnGoogle.visibility = View.GONE
        }
    }

    // 🔹 Función para validar si la URL de YouTube es válida
    private fun isValidYouTubeUrl(url: String): Boolean {
        return url.isNotEmpty() && url != "Sin video" &&
                (url.contains("youtube.com") || url.contains("youtu.be"))
    }

    // 🔹 Función para validar si la URL de Google es válida
    private fun isValidGoogleUrl(url: String): Boolean {
        return url.isNotEmpty() && url != "Sin enlace" &&
                (url.startsWith("http://") || url.startsWith("https://"))
    }
}
