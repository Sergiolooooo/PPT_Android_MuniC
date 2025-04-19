package com.example.ppt_munic.data.albumComercio

object AlbumCache {
    // Mapa en memoria: key = idComercio, value = lista de imágenes del álbum
    val cache: MutableMap<Int, List<albumComercio>> = mutableMapOf()
}
