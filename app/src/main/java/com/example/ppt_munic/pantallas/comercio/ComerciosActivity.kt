package com.example.ppt_munic.pantallas.comercio

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.comercio.ComercioAdapter
import com.example.ppt_munic.network.RetrofitClient
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            "HOTELES" -> iconoCategoria.setImageResource(R.drawable.ic_hoteles)
            "COMIDAS" -> iconoCategoria.setImageResource(R.drawable.ic_sol)
            else -> iconoCategoria.setImageResource(R.drawable.ic_default)
        }

        tituloCategoria.text = categoria

        btnCerrar.setOnClickListener {
            finish() // Cierra la actividad
        }

        obtenerComerciosPorCategoria(categoria)
    }

    private fun obtenerComerciosPorCategoria(categoria: String) {
        lifecycleScope.launch(Dispatchers.IO) {  // Ejecuta en un hilo de fondo
            try {
                val response = RetrofitClient.api.getComerciosByCategoria(categoria).execute()
                if (response.isSuccessful) {
                    val comercios = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {  // Actualiza la UI en el hilo principal
                        comercioAdapter = ComercioAdapter(comercios, iconoCategoria.drawable)
                        recyclerView.adapter = comercioAdapter
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API_ERROR", "Error en la llamada: ${e.message}")
                }
            }
        }
    }
}
