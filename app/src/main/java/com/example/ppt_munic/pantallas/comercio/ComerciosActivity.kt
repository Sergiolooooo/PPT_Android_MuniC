package com.example.ppt_munic.pantallas.comercio

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
    private lateinit var iconoCategoria: ImageView
    private lateinit var tituloCategoria: TextView
    private lateinit var btnCerrar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comercios)

        recyclerView = findViewById(R.id.recyclerViewComercios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        iconoCategoria = findViewById(R.id.iconoCategoria)
        tituloCategoria = findViewById(R.id.titulo_categoria)
        btnCerrar = findViewById(R.id.btn_cerrar)

        val categoria = intent.getStringExtra("categoria") ?: ""

        // Mostrar el icono correcto de la categoría
        when (categoria) {
            "DEPORTES" -> iconoCategoria.setImageResource(R.drawable.ic_comercio)
            "FRUTAS" -> iconoCategoria.setImageResource(R.drawable.ic_sol)
            "VERDURAS" -> iconoCategoria.setImageResource(R.drawable.ic_comercio)
            else -> iconoCategoria.setImageResource(R.drawable.ic_default)
        }

        tituloCategoria.text = categoria

        btnCerrar.setOnClickListener {
            finish() // Cierra la actividad
        }

        obtenerComerciosPorCategoria(categoria)
    }

    private fun obtenerComerciosPorCategoria(categoria: String) {
        RetrofitClient.api.getComerciosByCategoria(categoria).enqueue(object : Callback<ComercioRespuesta> {
            override fun onResponse(call: Call<ComercioRespuesta>, response: Response<ComercioRespuesta>) {
                if (response.isSuccessful) {
                    val comercios = response.body()?.data ?: emptyList()
                    comercioAdapter = ComercioAdapter(comercios, iconoCategoria.drawable)
                    recyclerView.adapter = comercioAdapter
                }
            }

            override fun onFailure(call: Call<ComercioRespuesta>, t: Throwable) {
                // Manejar error
            }
        })
    }
}
