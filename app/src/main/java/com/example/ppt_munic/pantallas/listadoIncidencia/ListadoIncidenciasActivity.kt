package com.example.ppt_munic.pantallas.listadoIncidencia

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.ppt_munic.R
import com.example.ppt_munic.data.listado_incidencias.ListadoIncidencia
import com.example.ppt_munic.data.listado_incidencias.ListadoIncidenciaAdapter
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.incidencia.IncidenciaActivity
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListadoIncidenciasActivity : DrawerActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListadoIncidenciaAdapter
    private lateinit var btnCerrar: ImageView
    private lateinit var titulo: TextView
    private lateinit var icono: ImageView
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_incidencias)

        recyclerView = findViewById(R.id.recyclerViewComercios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnCerrar = findViewById(R.id.btn_cerrar)
        titulo = findViewById(R.id.titulo_categoria)
        icono = findViewById(R.id.iconoCategoria)
        searchBar = findViewById(R.id.search_bar)

        icono.setImageResource(R.drawable.ic_default)

        btnCerrar.setOnClickListener { finish() }

        obtenerListadoIncidencias()
    }

    private fun obtenerListadoIncidencias() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getListadoIncidencias().execute()
                if (response.isSuccessful && response.body()?.success == true) {
                    val listado: List<ListadoIncidencia> = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        adapter = ListadoIncidenciaAdapter(listado) { incidencia ->
                            val intent = Intent(this@ListadoIncidenciasActivity, IncidenciaActivity::class.java)
                            intent.putExtra("id_incidencia", incidencia.id_incidencia)
                            intent.putExtra("nombre_incidencia", incidencia.nombre_incidencia)
                            startActivity(intent)
                        }
                        adapter.setListaCompleta(listado)
                        recyclerView.adapter = adapter

                        searchBar.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                adapter.filtrar(s.toString())
                            }
                            override fun afterTextChanged(s: Editable?) {}
                        })
                    }
                } else {
                    // Error silenciado en release
                }
            } catch (_: Exception) {
                // Error silenciado en release
            }
        }
    }
}
