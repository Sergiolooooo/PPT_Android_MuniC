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
        btnCerrar = findViewById(R.id.btn_cerrar)
        val btnYouTube: ImageView = findViewById(R.id.btnYouTube)


        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val telefono = intent.getStringExtra("telefono")
        val videoUrl = intent.getStringExtra("video_youtube")
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

        if (!videoUrl.isNullOrEmpty()) {
            btnYouTube.visibility = View.VISIBLE // 🔹 Si hay video, mostrar icono
            btnYouTube.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }
        } else {
            btnYouTube.visibility = View.GONE // 🔹 Si no hay video, ocultar icono
        }
    }

}
