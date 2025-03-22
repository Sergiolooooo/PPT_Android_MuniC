package com.example.ppt_munic.pantallas.albumComercio

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ppt_munic.R
import com.github.chrisbanes.photoview.PhotoView
import java.io.File

class ImagenCompletaActivity : AppCompatActivity() {

    private lateinit var photoView: PhotoView
    private lateinit var btnClose: ImageView
    private var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagen_completa)

        photoView = findViewById(R.id.photo_view)
        btnClose = findViewById(R.id.btn_close)

        imagePath = intent.getStringExtra("pathImagen")

        imagePath?.let { path ->
            val bitmap = BitmapFactory.decodeFile(path)
            photoView.setImageBitmap(bitmap)
        }

        btnClose.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Borra la imagen temporal al cerrar la pantalla
        imagePath?.let {
            File(it).delete()
        }
    }
}
