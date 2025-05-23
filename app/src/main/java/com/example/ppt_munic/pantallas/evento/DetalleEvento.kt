package com.example.ppt_munic.pantallas.evento

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ppt_munic.R
import com.example.ppt_munic.data.evento.EventoCache
import com.example.ppt_munic.pantallas.albumComercio.ImagenCompletaActivity
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import java.io.FileOutputStream

class DetalleEvento : DrawerActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var iconoEncabezado: ImageView
    private lateinit var imgPreview: ShapeableImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvFecha: TextView
    private lateinit var tvFechaFin: TextView
    private lateinit var tvLugar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_evento)

        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoEncabezado = findViewById(R.id.iconoComercio)
        imgPreview = findViewById(R.id.imgPreview)
        tvNombre = findViewById(R.id.tvNombre)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvFecha = findViewById(R.id.tvFecha)
        tvFechaFin = findViewById(R.id.tvFechaFin)
        tvLugar = findViewById(R.id.tvLugar)

        val id = intent.getIntExtra("id_evento", -1)
        val evento = EventoCache.cache[id]

        if (evento != null) {
            tvNombre.text = evento.nombre
            tvDescripcion.text = evento.descripcion
            tvFecha.text = evento.fecha.take(10)
            tvFechaFin.text = evento.fechaFin.take(10)
            tvLugar.text = evento.lugar
            iconoEncabezado.setImageResource(IconosEvento.iconoEvento)

            if (!evento.imagen.isNullOrEmpty()) {
                Glide.with(this)
                    .load(evento.imagen)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(imgPreview)
            } else {
                imgPreview.setImageResource(R.drawable.ic_default)
            }

            imgPreview.setOnClickListener {
                val imagenBase64 = evento.imagen
                if (!imagenBase64.isNullOrEmpty()) {
                    try {
                        val base64Clean = imagenBase64.substringAfter(",")
                        val imageBytes = Base64.decode(base64Clean, Base64.DEFAULT)
                        val tempFile = File.createTempFile("imagen_evento_", ".jpg", cacheDir)
                        FileOutputStream(tempFile).use { it.write(imageBytes) }

                        val intent = Intent(this, ImagenCompletaActivity::class.java)
                        intent.putExtra("pathImagen", tempFile.absolutePath)
                        startActivity(intent)
                    } catch (_: Exception) {
                        // Error silencioso
                    }
                }
            }

        } else {
            finish()
        }

        btnCerrar.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val id = intent.getIntExtra("id_evento", -1)
        EventoCache.cache.remove(id)
    }
}
