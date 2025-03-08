package com.example.ppt_munic.data.Productos

data class Producto(
    val id_producto: Int,
    val nombre_producto: String,
    val descripcion_producto: String,
    val precio: Double,
    val id_comercio: Int
)
