package com.example.ppt_munic.pantallas.categoria

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.ppt_munic.R

object AsignarImagenCategoria {
    fun cargar(context: Context, imagenUrl: String?, imageView: ImageView) {
        if (!imagenUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imagenUrl)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.ic_default)
        }
    }
}
