package com.example.ppt_munic.pantallas.evento

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
import com.example.ppt_munic.data.evento.Evento
import com.example.ppt_munic.data.evento.EventoAdapter
import com.example.ppt_munic.data.evento.EventoCache
import com.example.ppt_munic.data.evento.EventoRespuesta
import com.example.ppt_munic.databinding.ActivityEventosBinding
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.example.ppt_munic.pantallas.menu.DrawerManager
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventosActivity : DrawerActivity() {

    private lateinit var binding: ActivityEventosBinding
    private lateinit var adapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout = binding.root.findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = binding.root.findViewById<NavigationView>(R.id.nav_view)
        val menuIcon = binding.menuIcon

        DrawerManager.setupDrawer(this, drawerLayout, navView, menuIcon)
        binding.iconoCategoria.setImageResource(IconosEvento.iconoEvento)

        adapter = EventoAdapter(emptyList()) { evento ->
            EventoCache.cache[evento.id] = evento
            val intent = Intent(this, DetalleEvento::class.java)
            intent.putExtra("id_evento", evento.id)
            startActivity(intent)
        }

        binding.recyclerEventos.layoutManager = LinearLayoutManager(this)
        binding.recyclerEventos.adapter = adapter

        binding.btnCerrar.setOnClickListener { finish() }

        obtenerEventos()
        configurarBuscador()
    }

    private fun obtenerEventos() {
        RetrofitClient.api.getEventos().enqueue(object : Callback<EventoRespuesta> {
            override fun onResponse(call: Call<EventoRespuesta>, response: Response<EventoRespuesta>) {
                if (response.isSuccessful) {
                    val eventos = response.body()?.data ?: emptyList()
                    adapter.setListaCompleta(eventos)
                } else {
                    Toast.makeText(this@EventosActivity, "Error al obtener eventos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EventoRespuesta>, t: Throwable) {
                Toast.makeText(this@EventosActivity, "No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
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
