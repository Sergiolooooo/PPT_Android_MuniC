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
        setContentView(R.layout.activity_comercios) // Layout para esta Activity

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Recibir el extra "categoria" enviado por CategoriasActivity
        val categoria = intent.getStringExtra("categoria")
        if (categoria != null) {
            cargarComerciosPorCategoria(categoria)
        } else {
            cargarTodosLosComercios()
        }
    }

    private fun cargarComerciosPorCategoria(categoria: String) {
        Log.d("DEBUG", "Cargando comercios para la categoría: $categoria") // Verifica qué se está enviando

        RetrofitClient.api.getComerciosByCategoria(categoria).enqueue(object : Callback<ComercioRespuesta> {
            override fun onResponse(call: Call<ComercioRespuesta>, response: Response<ComercioRespuesta>) {
                if (response.isSuccessful) {
                    val comercioResponse = response.body()
                    if (comercioResponse != null) {
                        Log.d("API_SUCCESS", "Comercios recibidos: ${comercioResponse.data}")
                        comercioAdapter = ComercioAdapter(comercioResponse.data)
                        recyclerView.adapter = comercioAdapter
                    } else {
                        Log.e("API_ERROR", "Respuesta vacía")
                    }
                } else {
                    Log.e("API_ERROR", "Código de error: ${response.code()}, mensaje: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ComercioRespuesta>, t: Throwable) {
                Log.e("API_ERROR", "Fallo en la llamada: ${t.message}")
            }
        })
    }

    private fun cargarTodosLosComercios() {
        // Implementa la llamada al endpoint que trae todos los comercios si se desea
    }
}