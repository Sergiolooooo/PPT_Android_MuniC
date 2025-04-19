package com.example.ppt_munic.pantallas.noticia

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ppt_munic.R
import com.example.ppt_munic.data.noticias.NoticiaCache
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import java.io.FileOutputStream

class DetalleNoticia : DrawerActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var iconoEncabezado: ImageView
    private lateinit var imgPreview: ShapeableImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvFecha: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DetalleNoticia", "✅ Activity lanzada")
        setContentView(R.layout.activity_detalle_noticia)

        // Vincular vistas
        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoEncabezado = findViewById(R.id.iconoComercio)
        imgPreview = findViewById(R.id.imgPreview)
        tvNombre = findViewById(R.id.tvNombre)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvFecha = findViewById(R.id.tvFecha)

        val id = intent.getIntExtra("id_noticia", -1)
        Log.d("DetalleNoticia", "Recibido ID de noticia: $id")

        val noticia = NoticiaCache.cache[id]

        if (noticia != null) {
            Log.d("DetalleNoticia", "Noticia encontrada en caché: ${noticia.titulo}")

            tvNombre.text = noticia.titulo
            tvDescripcion.text = noticia.contenido
            tvFecha.text = noticia.fecha_publicacion.take(10)
            iconoEncabezado.setImageResource(IconosNoticia.iconoNoticia)

            if (!noticia.imagen.isNullOrEmpty()) {
                Glide.with(this)
                    .load(noticia.imagen)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(imgPreview)
            } else {
                imgPreview.setImageResource(R.drawable.ic_default)
            }

            imgPreview.setOnClickListener {
                Log.d("DetalleNoticia", "Imagen tocada, preparando vista completa...")

                val imagenBase64 = noticia.imagen
                if (!imagenBase64.isNullOrEmpty()) {
                    try {
                        val base64Clean = imagenBase64.substringAfter(",")
                        val imageBytes = Base64.decode(base64Clean, Base64.DEFAULT)
                        val tempFile = File.createTempFile("imagen_noticia_", ".jpg", cacheDir)
                        FileOutputStream(tempFile).use { it.write(imageBytes) }

                        val intent = Intent(this, com.example.ppt_munic.pantallas.albumComercio.ImagenCompletaActivity::class.java)
                        intent.putExtra("pathImagen", tempFile.absolutePath)
                        startActivity(intent)
                    } catch (e: Exception) {
                        Log.e("DetalleNoticia", "Error al convertir imagen base64: ${e.message}")
                    }
                } else {
                    Log.e("DetalleNoticia", "Imagen base64 vacía o nula")
                }
            }

        } else {
            Log.e("DetalleNoticia", "No se encontró la noticia en caché. Cerrando.")
            finish()
        }

        btnCerrar.setOnClickListener {
            Log.d("DetalleNoticia", "Cierre manual de pantalla")
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val id = intent.getIntExtra("id_noticia", -1)
        Log.d("DetalleNoticia", "onDestroy → eliminando noticia ID $id del caché")
        NoticiaCache.cache.remove(id)
    }
}
