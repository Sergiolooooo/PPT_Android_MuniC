package com.example.ppt_munic.data.albumComercio

data class DatosImagen(
    val data: List<Int> // <- ahora es una lista de números (bytes)
)

data class albumComercio(
    val datos_imagen: DatosImagen,
    val tipo_imagen: String
)