package com.example.ppt_munic.data.comercio

import com.google.gson.annotations.SerializedName

data class Comercio(
    @SerializedName("id_comercio") val id: Int,
    @SerializedName("nombre_comercio") val nombre: String,
    @SerializedName("descripcion_comercio") val descripcion: String,
    @SerializedName("url_google") val google: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("video_youtube") val videoYoutube: String?,
    @SerializedName("categoria") val categoria: String
)