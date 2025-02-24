package com.example.ppt_munic.pantallas.categoria

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.categoria.Categoria
import com.example.ppt_munic.data.categoria.CategoriaAdapter
import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        recyclerView = findViewById(R.id.recyclerViewCategorias)
        recyclerView.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)


        categoriaAdapter = CategoriaAdapter(emptyList())
        recyclerView.adapter = categoriaAdapter

        obtenerCategorias()
    }

    private fun obtenerCategorias() {
        RetrofitClient.api.getCategorias().enqueue(object : Callback<CategoriaRespuesta> {
            override fun onResponse(call: Call<CategoriaRespuesta>, response: Response<CategoriaRespuesta>) {
                if (response.isSuccessful) {
                    val categoriaResponse = response.body()
                    val categorias: List<Categoria> = categoriaResponse?.data ?: emptyList()

                    // 🔹 AQUÍ SE LLAMA actualizarLista() para actualizar el RecyclerView
                    categoriaAdapter.actualizarLista(categorias)
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoriaRespuesta>, t: Throwable) {
                Log.e("API_ERROR", "Fallo en la llamada: ${t.message}")
            }
        })
    }


}
