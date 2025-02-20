package com.example.ppt_munic.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

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