package com.example.ppt_munic.network

import com.example.ppt_munic.data.Productos.ProductosRespuesta
import com.example.ppt_munic.data.Redes_Sociales.RedesRespuesta
import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.data.comercio.ComercioRespuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/categoriascomercios")
    fun getCategorias(): Call<CategoriaRespuesta>

    @GET("api/comercios")
    fun getComercios(): Call<ComercioRespuesta>

    @GET("api/comercios/categoria")
    fun getComerciosByCategoria(@Query("categoria") categoria: String): Call<ComercioRespuesta>

    @GET("api/redesSociales/comercio") // RUTA PARA OBTENER REDES POR COMERCIO
    fun getRedesByComercio(@Query("comercio") comercio: Int): Call<RedesRespuesta>

    @GET("api/productos/productos") // ✅ Ahora coincide con el backend
    fun getProductosByComercio(@Query("comercio") comercio: Int): Call<ProductosRespuesta>

}