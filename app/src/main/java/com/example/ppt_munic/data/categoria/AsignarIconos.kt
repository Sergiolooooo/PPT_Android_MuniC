package com.example.ppt_munic.data.categoria

import com.example.ppt_munic.R

object AsignarIconos {
    fun obtenerIconoPorCategoria(nombreCategoria: String): Int {
        return when (nombreCategoria.uppercase()) {
            "DEPORTES" -> R.drawable.ic_comercio
            "HOTELES" -> R.drawable.ic_hoteles
            "COMIDAS" -> R.drawable.ic_sol
            else -> R.drawable.ic_default
        }
    }
}