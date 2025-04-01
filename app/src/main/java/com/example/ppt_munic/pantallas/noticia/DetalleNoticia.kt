package com.example.ppt_munic.pantallas.noticia

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.menu.DrawerActivity

class DetalleNoticia : DrawerActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var iconoComercio: ImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvFecha: TextView
    private lateinit var tvTelefono: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_noticia)

        // Vincular vistas manualmente
        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoComercio = findViewById(R.id.iconoComercio)
        tvNombre = findViewById(R.id.tvNombre)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvFecha = findViewById(R.id.tvFecha)
        tvTelefono = findViewById(R.id.tvTelefono)

        // Recibir datos de la noticia
        val titulo = intent.getStringExtra("titulo")
        val contenido = intent.getStringExtra("contenido")
        val fecha = intent.getStringExtra("fecha")
        val autor = intent.getStringExtra("autor")

        // Mostrar datos en pantalla
        tvNombre.text = titulo
        tvDescripcion.text = contenido
        tvFecha.text = fecha
        tvTelefono.text = autor
        iconoComercio.setImageResource(R.drawable.ic_default)

        // Botón para cerrar la pantalla
        btnCerrar.setOnClickListener {
            finish()
        }
    }
}
