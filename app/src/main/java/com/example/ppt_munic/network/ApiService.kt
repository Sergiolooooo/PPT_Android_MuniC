package com.example.ppt_munic.network

import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.data.comercio.ComercioRespuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/categoriascomercios") // RUTA CORRECTA: SIN "/" AL INICIO
    fun getCategorias(): Call<CategoriaRespuesta>

    @GET("api/comercios") // RUTA CORRECTA: SIN "/" AL INICIO
    fun getComercios(): Call<ComercioRespuesta>

    @GET("api/comercios/categoria") // RUTA CORRECTA PARA FILTRAR POR CATEGORÍA
    fun getComerciosByCategoria(@Query("categoria") categoria: String): Call<ComercioRespuesta>
}
