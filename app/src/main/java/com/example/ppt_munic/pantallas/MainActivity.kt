package com.example.ppt_munic.pantallas

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.ComercioAdapter
import com.example.ppt_munic.data.ComercioRespuesta
import com.example.ppt_munic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cargarComercios()
    }

    private fun cargarComercios() {
        RetrofitClient.api.getComercios().enqueue(object : Callback<ComercioRespuesta> {
            override fun onResponse(call: Call<ComercioRespuesta>, response: Response<ComercioRespuesta>) {
                if (response.isSuccessful) {
                    val comercioResponse = response.body()
                    recyclerView.adapter = ComercioAdapter(comercioResponse?.data ?: emptyList())
                } else {
                    Log.e("API_ERROR", "Respuesta no exitosa")
                }
            }

            override fun onFailure(call: Call<ComercioRespuesta>, t: Throwable) {
                Log.e("API_ERROR", "Error al obtener datos: ${t.message}")
            }
        })
    }
}
