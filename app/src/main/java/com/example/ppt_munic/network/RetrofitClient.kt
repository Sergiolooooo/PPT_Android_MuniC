package com.example.ppt_munic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://ppt-munic.onrender.com/" // Asegúrate de incluir la barra al final

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Configura la URL base
            .addConverterFactory(GsonConverterFactory.create()) // Convierte JSON a objetos Kotlin
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java) // Crea la instancia de ApiService
    }
}
