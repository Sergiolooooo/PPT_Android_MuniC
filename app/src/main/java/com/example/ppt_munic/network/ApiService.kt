package com.example.ppt_munic.network

import com.example.ppt_munic.data.listado_incidencias.ListadoIncidenciaRespuesta
import com.example.ppt_munic.data.productos.ProductosRespuesta
import com.example.ppt_munic.data.redes_sociales.RedesRespuesta
import com.example.ppt_munic.data.albumComercio.AlbumComercioRespuesta
import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.data.comercio.ComercioRespuesta
import com.example.ppt_munic.data.incidencia.IncidenciaRespuesta
import com.example.ppt_munic.data.noticias.NoticiaRespuesta
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("api/noticias")
    fun getNoticias(): Call<NoticiaRespuesta>

    @Multipart
    @POST("api/incidencias")
    fun postIncidencia(
        @Part("nombre_reportante") nombreReportante: RequestBody,
        @Part("cedula_reportante") cedulaReportante: RequestBody,
        @Part("telefono_reportante") telefonoReportante: RequestBody,
        @Part("descripcion_incidencia") descripcionIncidencia: RequestBody,
        @Part("id_incidencia") idIncidencia: RequestBody,
        @Part("provincia") provincia: RequestBody,
        @Part("canton") canton: RequestBody,
        @Part("distrito") distrito: RequestBody,
        @Part("direccion_exacta") direccionExacta: RequestBody,
        @Part imagen: MultipartBody.Part
    ): Call<IncidenciaRespuesta>

    @GET("api/listadoIncidencias")
    fun getListadoIncidencias(): Call<ListadoIncidenciaRespuesta>

    @GET("api/eventos")
    fun getEventos(): Call<com.example.ppt_munic.data.evento.EventoRespuesta>


}