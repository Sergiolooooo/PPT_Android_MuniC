package com.example.ppt_munic.pantallas.albumComercio

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.albumComercio.AlbumComercioAdapter
import com.example.ppt_munic.data.albumComercio.AlbumComercioRespuesta
import com.example.ppt_munic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AlbumComercioActivity : AppCompatActivity() {

    private lateinit var recyclerAlbum: RecyclerView
    private lateinit var btnCerrar: ImageView
    private lateinit var iconoComercio: ImageView
    private var iconoResId: Int = R.drawable.ic_default
    private lateinit var adapter: AlbumComercioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_comercio)

        recyclerAlbum = findViewById(R.id.recycler_album)
        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoComercio = findViewById(R.id.iconoComercio)

        recyclerAlbum.layoutManager = GridLayoutManager(this, 2)

        val idComercio = intent.getIntExtra("id_comercio", 0)
        iconoResId = intent.getIntExtra("iconoCategoria", R.drawable.ic_default)
        iconoComercio.setImageResource(iconoResId)

        adapter = AlbumComercioAdapter(mutableListOf()) { byteArray ->
            abrirImagenCompleta(byteArray)
        }
        recyclerAlbum.adapter = adapter

        obtenerAlbum(idComercio)

        btnCerrar.setOnClickListener { finish() }
    }

    private fun obtenerAlbum(comercioId: Int) {
        val apiService = RetrofitClient.api

        apiService.getAlbumByComercio(comercioId).enqueue(object : Callback<AlbumComercioRespuesta> {
            override fun onResponse(call: Call<AlbumComercioRespuesta>, response: Response<AlbumComercioRespuesta>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val albumList = response.body()?.data?.toMutableList() ?: mutableListOf()
                    adapter.actualizarLista(albumList)
                }
            }

            override fun onFailure(call: Call<AlbumComercioRespuesta>, t: Throwable) {
                // Manejo simple de error
            }
        })
    }

    private fun abrirImagenCompleta(byteArray: ByteArray) {
        val file = File.createTempFile("imagen_temp", ".jpg", cacheDir)
        file.writeBytes(byteArray)

        val intent = Intent(this, ImagenCompletaActivity::class.java)
        intent.putExtra("pathImagen", file.absolutePath)
        startActivity(intent)
    }
}
