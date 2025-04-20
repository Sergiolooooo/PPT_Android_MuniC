package com.example.ppt_munic.data.listado_Incidencias

data class ListadoIncidenciaRespuesta(
    val success: Boolean,
    val data: List<ListadoIncidencia>
)