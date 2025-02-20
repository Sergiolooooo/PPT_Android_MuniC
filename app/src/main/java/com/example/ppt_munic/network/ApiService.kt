package com.example.ppt_munic.network

import com.example.ppt_munic.data.ComercioRespuesta
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("comercios/") // Ruta de la API
    fun getComercios(): Call<ComercioRespuesta> // Retorna un objeto que contiene la lista de Comercio
}
