package com.example.ppt_munic.pantallas.noticia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ppt_munic.R
import com.example.ppt_munic.databinding.ActivityDetalleNoticiaBinding

class DetalleNoticia : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleNoticiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleNoticiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir datos de la noticia
        val titulo = intent.getStringExtra("titulo")
        val contenido = intent.getStringExtra("contenido")
        val fecha = intent.getStringExtra("fecha")
        val autor = intent.getStringExtra("autor")

        binding.tvNombre.text = titulo
        binding.tvDescripcion.text = contenido
        binding.tvFecha.text = fecha
        binding.tvTelefono.text = autor
        binding.iconoComercio.setImageResource(R.drawable.ic_default)

        // Manejo de botón cerrar
        binding.btnCerrar.setOnClickListener { finish() }
    }
}
