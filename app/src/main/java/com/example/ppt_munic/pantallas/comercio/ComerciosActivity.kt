package com.example.ppt_munic.pantallas.comercio

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.comercio.ComercioAdapter
import com.example.ppt_munic.data.categoria.AsignarIconos
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope

class ComerciosActivity : DrawerActivity() {

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

        // Asignar ícono y título
        val iconoResId = AsignarIconos.obtenerIconoPorCategoria(categoria)
        iconoCategoria.setImageResource(iconoResId)
        tituloCategoria.text = categoria

        btnCerrar.setOnClickListener {
            finish()
        }

        obtenerComerciosPorCategoria(categoria, iconoResId)
    }

    private fun obtenerComerciosPorCategoria(categoria: String, iconoResId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getComerciosByCategoria(categoria).execute()
                if (response.isSuccessful) {
                    val comercios = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        comercioAdapter = ComercioAdapter(comercios, iconoResId)
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
