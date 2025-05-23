package com.example.ppt_munic.pantallas.incidencia

import com.example.ppt_munic.data.incidencia.IncidenciaRespuesta
import com.example.ppt_munic.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EnviarIncidencia {

    fun enviar(
        nombre: String,
        cedula: Int,
        telefono: Int,
        descripcion: String,
        idIncidencia: Int,
        provincia: String,
        canton: String,
        distrito: String,
        direccion: String,
        imagenFile: File,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = RetrofitClient.api

        val nombreBody = nombre.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val cedulaBody = cedula.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val telefonoBody = telefono.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val descripcionBody = descripcion.toRequestBody("multipart/form-data".toMediaTypeOrNull()) // ✅
        val idIncidenciaBody = idIncidencia.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val provinciaBody = provincia.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val cantonBody = canton.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val distritoBody = distrito.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val direccionBody = direccion.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val mimeType = when (imagenFile.extension.lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            else -> "image/jpeg"
        }
        val imagenRequest = imagenFile.asRequestBody(mimeType.toMediaTypeOrNull())
        val imagenPart = MultipartBody.Part.createFormData("imagen", imagenFile.name, imagenRequest)

        val call = apiService.postIncidencia(
            nombreBody,
            cedulaBody,
            telefonoBody,
            descripcionBody,
            idIncidenciaBody,
            provinciaBody,
            cantonBody,
            distritoBody,
            direccionBody,
            imagenPart
        )

        call.enqueue(object : Callback<IncidenciaRespuesta> {
            override fun onResponse(call: Call<IncidenciaRespuesta>, response: Response<IncidenciaRespuesta>) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        onSuccess(resp.message)
                    } else {
                        onError(resp?.message ?: "Ocurrió un error inesperado.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    onError("Error ${response.code()}: ${errorBody ?: "No se pudo procesar la solicitud"}")
                }
            }

            override fun onFailure(call: Call<IncidenciaRespuesta>, t: Throwable) {
                onError("Error de conexión: ${t.message ?: "Error desconocido"}")
            }
        })
    }
}
