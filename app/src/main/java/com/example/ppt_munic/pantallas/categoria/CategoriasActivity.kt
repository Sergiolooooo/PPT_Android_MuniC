package com.example.ppt_munic.pantallas.categoria

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.categoria.CategoriaAdapter
import com.example.ppt_munic.data.categoria.CategoriaRespuesta
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.menu.DrawerManager
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        menuIcon = findViewById(R.id.menu_icon)
        recyclerView = findViewById(R.id.recyclerViewCategorias)

        // Configurar RecyclerView con Grid de 3 columnas
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gridLayoutManager
        categoriaAdapter = CategoriaAdapter(emptyList())
        recyclerView.adapter = categoriaAdapter

        // Obtener categorías desde API
        obtenerCategorias()

        // Abre/cierra el Drawer con tu propio botón (menu_icon)
        menuIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Listener para las opciones del Drawer
        DrawerManager.setupDrawer(this, drawerLayout, navView, menuIcon)
    }

    private fun obtenerCategorias() {
        RetrofitClient.api.getCategorias().enqueue(object : Callback<CategoriaRespuesta> {
            override fun onResponse(call: Call<CategoriaRespuesta>, response: Response<CategoriaRespuesta>) {
                if (response.isSuccessful) {
                    val categorias = response.body()?.data ?: emptyList()
                    categoriaAdapter.actualizarLista(categorias)
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()}")
                    Toast.makeText(this@CategoriasActivity, "Error al obtener datos", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CategoriaRespuesta>, t: Throwable) {
                Log.e("API_ERROR", "Fallo en la llamada: ${t.message}")
                Toast.makeText(this@CategoriasActivity, "No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
            }
        })
    }
}
