package com.example.ppt_munic.pantallas.comercio

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ppt_munic.R

class DetalleComercio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comercio)

        val imgComercio: ImageView = findViewById(R.id.imgComercioDetalle)
        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvLatitud: TextView = findViewById(R.id.tvLatitud)
        val tvLongitud: TextView = findViewById(R.id.tvLongitud)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)
        val tvCategoria: TextView = findViewById(R.id.tvCategoria)

        // Recibir los datos
        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val latitud = intent.getStringExtra("latitud") ?: "N/A"
        val longitud = intent.getStringExtra("longitud") ?: "N/A"
        val telefono = intent.getIntExtra("telefono", 0).toString()
        val categoria = intent.getStringExtra("categoria") ?: "Sin categoría"

        // Asignar valores a los TextView
        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        tvLatitud.text = "Latitud: $latitud"
        tvLongitud.text = "Longitud: $longitud"
        tvTelefono.text = "Teléfono: $telefono"
        tvCategoria.text = "Categoría: $categoria"

        // Usar ic_default como imagen fija
        imgComercio.setImageResource(R.drawable.ic_default)
    }
}
