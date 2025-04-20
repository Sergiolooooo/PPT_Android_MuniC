package com.example.ppt_munic.pantallas.categoria

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.categoria.CategoriaAdapter
import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriasActivity : DrawerActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        recyclerView = findViewById(R.id.recyclerViewCategorias)
        searchBar = findViewById(R.id.search_bar)

        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gridLayoutManager
        categoriaAdapter = CategoriaAdapter(emptyList())
        recyclerView.adapter = categoriaAdapter

        obtenerCategorias()
        configurarBuscador()
    }

    private fun obtenerCategorias() {
        RetrofitClient.api.getCategorias().enqueue(object : Callback<CategoriaRespuesta> {
            override fun onResponse(call: Call<CategoriaRespuesta>, response: Response<CategoriaRespuesta>) {
                if (response.isSuccessful) {
                    val categorias = response.body()?.data ?: emptyList()
                    categoriaAdapter.setListaCompleta(categorias)
                } else {
                    Toast.makeText(this@CategoriasActivity, "Error al obtener datos", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CategoriaRespuesta>, t: Throwable) {
                Toast.makeText(this@CategoriasActivity, "No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configurarBuscador() {
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                categoriaAdapter.filtrar(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
