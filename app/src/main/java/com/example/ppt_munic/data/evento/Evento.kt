package com.example.ppt_munic.data.evento

import com.google.gson.annotations.SerializedName

data class Evento(
    @SerializedName("id_evento") val id: Int,
    @SerializedName("nombre_evento") val nombre: String,
    @SerializedName("descripcion_evento") val descripcion: String,
    @SerializedName("fecha_evento") val fecha: String,
    @SerializedName("lugar") val lugar: String,

)
