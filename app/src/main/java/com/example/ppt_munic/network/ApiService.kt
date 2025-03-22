package com.example.ppt_munic.network

import com.example.ppt_munic.data.Productos.ProductosRespuesta
import com.example.ppt_munic.data.Redes_Sociales.RedesRespuesta
import com.example.ppt_munic.data.albumComercio.AlbumComercioRespuesta
import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.data.comercio.ComercioRespuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/categoriascomercios")
    fun getCategorias(): Call<CategoriaRespuesta>

    @GET("api/comercios/categoria")
    fun getComerciosByCategoria(@Query("categoria") categoria: String): Call<ComercioRespuesta>

    @GET("api/redesSociales/comercio/{id}") // Cambiamos a params con {id}
    fun getRedesByComercio(@Path("id") id: Int): Call<RedesRespuesta>

    @GET("api/productos/comercio/{id}")
    fun getProductosByComercio(@Path("id") id: Int): Call<ProductosRespuesta>

    @GET("api/albumComercio/comercio/{id}")
    fun getAlbumByComercio(@Path("id") comercioId: Int): Call<AlbumComercioRespuesta>

}