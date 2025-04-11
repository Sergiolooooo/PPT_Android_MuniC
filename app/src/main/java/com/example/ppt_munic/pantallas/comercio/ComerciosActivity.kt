package com.example.ppt_munic.pantallas.comercio

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.comercio.ComercioAdapter
import com.example.ppt_munic.data.categoria.CategoriaSeleccionada
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.categoria.AsignarImagenCategoria
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
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comercios)

        recyclerView = findViewById(R.id.recyclerViewComercios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        iconoCategoria = findViewById(R.id.iconoCategoria)
        tituloCategoria = findViewById(R.id.titulo_categoria)
        btnCerrar = findViewById(R.id.btn_cerrar)
        searchBar = findViewById(R.id.search_bar)

        val categoria = intent.getStringExtra("categoria") ?: ""
        val imagen = CategoriaSeleccionada.imagen

        AsignarImagenCategoria.cargar(this, imagen, iconoCategoria)
        tituloCategoria.text = categoria

        btnCerrar.setOnClickListener {
            finish()
        }

        obtenerComerciosPorCategoria(categoria, imagen)
    }

    private fun obtenerComerciosPorCategoria(categoria: String, imagen: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getComerciosByCategoria(categoria).execute()
                if (response.isSuccessful) {
                    val comercios = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        comercioAdapter = ComercioAdapter(comercios, imagen)
                        comercioAdapter.setListaCompleta(comercios)
                        recyclerView.adapter = comercioAdapter

                        searchBar.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                comercioAdapter.filtrar(s.toString())
                            }
                            override fun afterTextChanged(s: Editable?) {}
                        })
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
