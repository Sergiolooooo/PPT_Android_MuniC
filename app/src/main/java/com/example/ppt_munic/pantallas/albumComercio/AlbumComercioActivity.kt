package com.example.ppt_munic.pantallas.albumComercio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.albumComercio.AlbumComercioAdapter
import com.example.ppt_munic.data.albumComercio.AlbumComercioRespuesta
import com.example.ppt_munic.data.albumComercio.AlbumCache
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.example.ppt_munic.data.categoria.CategoriaSeleccionada
import com.example.ppt_munic.pantallas.categoria.AsignarImagenCategoria
import com.example.ppt_munic.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AlbumComercioActivity : DrawerActivity() {

    private lateinit var recyclerAlbum: RecyclerView
    private lateinit var btnCerrar: ImageView
    private lateinit var iconoComercio: ImageView
    private lateinit var adapter: AlbumComercioAdapter
    private var comercioId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_comercio)

        recyclerAlbum = findViewById(R.id.recycler_album)
        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoComercio = findViewById(R.id.iconoComercio)

        recyclerAlbum.layoutManager = GridLayoutManager(this, 2)

        comercioId = intent.getIntExtra("id_comercio", 0)
        val imagenBase64 = CategoriaSeleccionada.imagen
        AsignarImagenCategoria.cargar(this, imagenBase64, iconoComercio)

        adapter = AlbumComercioAdapter(mutableListOf()) { byteArray ->
            abrirImagenCompleta(byteArray)
        }
        recyclerAlbum.adapter = adapter

        // 1. Verificar si en el AlbumCache ya precargamos algo.
        val precargado = AlbumCache.cache[comercioId]
        if (precargado != null) {
            // Sí, ya tenemos las imágenes en RAM
            adapter.actualizarLista(precargado)
            Log.d("ALBUM", "Cargado desde AlbumCache en memoria.")
        } else {
            // 2. Si no existe en AlbumCache, llamar a la API
            obtenerAlbum(comercioId)
        }

        btnCerrar.setOnClickListener { finish() }
    }


    private fun obtenerAlbum(comercioId: Int) {
        RetrofitClient.api.getAlbumByComercio(comercioId).enqueue(object : Callback<AlbumComercioRespuesta> {
            override fun onResponse(
                call: Call<AlbumComercioRespuesta>,
                response: Response<AlbumComercioRespuesta>
            ) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val albumList = response.body()?.data?.toMutableList() ?: mutableListOf()
                    adapter.actualizarLista(albumList)
                    Log.d("ALBUM", "Cargado desde la API (no había caché).")
                }
            }

            override fun onFailure(call: Call<AlbumComercioRespuesta>, t: Throwable) {
                Log.e("ALBUM", "Error en la carga: ${t.message}")
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
