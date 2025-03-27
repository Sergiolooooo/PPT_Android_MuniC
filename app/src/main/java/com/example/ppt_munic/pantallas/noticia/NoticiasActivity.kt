package com.example.ppt_munic.pantallas.noticia

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ppt_munic.data.noticias.Noticia
import com.example.ppt_munic.data.noticias.NoticiaRespuesta
import com.example.ppt_munic.databinding.ActivityNoticiasBinding
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.example.ppt_munic.ui.noticias.NoticiasAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticiasActivity : DrawerActivity() {

    private lateinit var binding: ActivityNoticiasBinding
    private lateinit var adapter: NoticiasAdapter
    private val listaNoticias = mutableListOf<Noticia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoticiasAdapter(listaNoticias) { noticia ->
            Toast.makeText(this, noticia.titulo, Toast.LENGTH_SHORT).show()
        }

        binding.recyclerNoticias.layoutManager = LinearLayoutManager(this)
        binding.recyclerNoticias.adapter = adapter

        obtenerNoticias()
    }

    private fun obtenerNoticias() {
        val apiService = RetrofitClient.api

        Log.d("NoticiasActivity", "Llamando a la API de noticias...")

        apiService.getNoticias().enqueue(object : Callback<NoticiaRespuesta> {
            override fun onResponse(call: Call<NoticiaRespuesta>, response: Response<NoticiaRespuesta>) {
                Log.d("NoticiasActivity", "URL: ${call.request().url}")

                if (response.isSuccessful) {
                    response.body()?.let { respuesta ->
                        Log.d("NoticiasActivity", "Noticias recibidas: ${respuesta.data.size}")
                        adapter.actualizarLista(respuesta.data)
                    }
                } else {
                    Log.e("NoticiasActivity", "Error HTTP ${response.code()}")
                    Log.e("NoticiasActivity", "Error Body: ${response.errorBody()?.string()}")
                    Toast.makeText(this@NoticiasActivity, "Error al obtener noticias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NoticiaRespuesta>, t: Throwable) {
                Log.e("NoticiasActivity", "Error en la API: ${t.message}")
                Toast.makeText(this@NoticiasActivity, "No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
            }
        })
    }
}