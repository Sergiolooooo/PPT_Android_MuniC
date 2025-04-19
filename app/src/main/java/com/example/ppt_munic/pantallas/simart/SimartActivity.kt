package com.example.ppt_munic.pantallas.simart

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.google.android.material.imageview.ShapeableImageView

class SimartActivity : DrawerActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var iconoEncabezado: ImageView
    private lateinit var imgPreview: ShapeableImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvFecha: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SimartActivity", "Pantalla Simart lanzada")
        setContentView(R.layout.activity_simart)

        // Vincular vistas
        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoEncabezado = findViewById(R.id.iconoComercio)
        imgPreview = findViewById(R.id.imgPreview)
        tvNombre = findViewById(R.id.tvNombre)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvFecha = findViewById(R.id.tvFecha)

        // Contenido placeholder
        tvNombre.text = "Simart"
        tvDescripcion.text = "Esta pantalla está reservada para futuras funcionalidades relacionadas con la plataforma Simart. ¡Pronto habrá más información!"
        tvFecha.text = "Próximamente"

        iconoEncabezado.setImageResource(R.drawable.ic_default)
        imgPreview.setImageResource(R.drawable.municipalidad)

        btnCerrar.setOnClickListener {
            finish()
        }
    }
}
