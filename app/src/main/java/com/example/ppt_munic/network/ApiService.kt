package com.example.ppt_munic.network

import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.data.comercio.ComercioRespuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categoriascomercios/")
    fun getCategorias(): Call<CategoriaRespuesta>

    @GET("comercios/categoria")
    fun getComerciosByCategoria(@Query("categoria") categoria: String): Call<ComercioRespuesta>

}
