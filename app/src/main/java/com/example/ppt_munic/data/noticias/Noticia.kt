package com.example.ppt_munic.data.noticias

data class Noticia(
    val id_noticia: Int,
    val titulo: String,
    val contenido: String,
    val fecha_publicacion: String,
    val autor: String,
    val imagen: String? // nueva propiedad base64
)
