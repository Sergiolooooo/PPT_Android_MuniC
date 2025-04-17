package com.example.ppt_munic.pantallas.noticia

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ppt_munic.R
import com.example.ppt_munic.data.noticias.Noticia
import com.example.ppt_munic.data.noticias.NoticiaRespuesta
import com.example.ppt_munic.data.noticias.NoticiasAdapter
import com.example.ppt_munic.databinding.ActivityNoticiasBinding
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.example.ppt_munic.pantallas.menu.DrawerManager
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticiasActivity : DrawerActivity() {

    private lateinit var binding: ActivityNoticiasBinding
    private lateinit var adapter: NoticiasAdapter
    private lateinit var btnCerrar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup del Drawer
        val drawerLayout = binding.root.findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = binding.root.findViewById<NavigationView>(R.id.nav_view)
        val menuIcon = binding.root.findViewById<ImageView>(R.id.menu_icon)
        val iconoCategoria = findViewById<ImageView>(R.id.iconoCategoria)
        btnCerrar = findViewById(R.id.btn_cerrar)

        DrawerManager.setupDrawer(this, drawerLayout, navView, menuIcon)

        // Icono decorativo centralizado
        iconoCategoria.setImageResource(IconosNoticia.iconoNoticia)

        // Configurar RecyclerView
        adapter = NoticiasAdapter(emptyList()) { noticia ->
            val intent = Intent(this, DetalleNoticia::class.java)
            intent.putExtra("id_noticia", noticia.id_noticia)
            startActivity(intent)
        }

        binding.recyclerEventos.layoutManager = LinearLayoutManager(this)
        binding.recyclerEventos.adapter = adapter

        btnCerrar.setOnClickListener {
            finish()
        }

        obtenerNoticias()
        configurarBuscador()
    }

    private fun obtenerNoticias() {
        RetrofitClient.api.getNoticias().enqueue(object : Callback<NoticiaRespuesta> {
            override fun onResponse(call: Call<NoticiaRespuesta>, response: Response<NoticiaRespuesta>) {
                if (response.isSuccessful) {
                    val noticias = response.body()?.data ?: emptyList()
                    adapter.setListaCompleta(noticias)
                } else {
                    Toast.makeText(this@NoticiasActivity, "Error al obtener noticias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NoticiaRespuesta>, t: Throwable) {
                Toast.makeText(this@NoticiasActivity, "No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configurarBuscador() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filtrar(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
