package com.example.ppt_munic.pantallas.comercio

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.comercio.ComercioAdapter
import com.example.ppt_munic.data.comercio.ComercioRespuesta
import com.example.ppt_munic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComerciosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var comercioAdapter: ComercioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comercios)

        recyclerView = findViewById(R.id.recyclerViewComercios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val categoria = intent.getStringExtra("categoria") ?: ""
        obtenerComerciosPorCategoria(categoria)
    }

    private fun obtenerComerciosPorCategoria(categoria: String) {
        RetrofitClient.api.getComerciosByCategoria(categoria).enqueue(object : Callback<ComercioRespuesta> {
            override fun onResponse(call: Call<ComercioRespuesta>, response: Response<ComercioRespuesta>) {
                if (response.isSuccessful) {
                    val comercios = response.body()?.data ?: emptyList()
                    comercioAdapter = ComercioAdapter(comercios)
                    recyclerView.adapter = comercioAdapter
                }
            }

            override fun onFailure(call: Call<ComercioRespuesta>, t: Throwable) {
                Log.e("API_ERROR", "Error en la llamada: ${t.message}")
            }
        })
    }
}
