package com.example.ppt_munic.pantallas

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ppt_munic.R

class DetalleComercio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comercio)

        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvLatitud: TextView = findViewById(R.id.tvLatitud)
        val tvLongitud: TextView = findViewById(R.id.tvLongitud)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)
        val tvCategoria: TextView = findViewById(R.id.tvCategoria)

        tvNombre.text = intent.getStringExtra("nombre")
        tvDescripcion.text = intent.getStringExtra("descripcion")
        tvLatitud.text = "Latitud: " + intent.getStringExtra("latitud")
        tvLongitud.text = "Longitud: " + intent.getStringExtra("longitud")
        tvTelefono.text = "Teléfono: " + intent.getIntExtra("telefono", 0)
        tvCategoria.text = "Categoría: " + intent.getStringExtra("categoria")
    }
}