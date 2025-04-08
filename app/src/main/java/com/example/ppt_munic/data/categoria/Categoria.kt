package com.example.ppt_munic.data.categoria

import com.google.gson.annotations.SerializedName

data class Categoria(
    @SerializedName("id_categoria") val id: Int,
    @SerializedName("nombre_categoria") val nombre: String,
    @SerializedName("imagen") val imagen: String? // Base64 directo
)