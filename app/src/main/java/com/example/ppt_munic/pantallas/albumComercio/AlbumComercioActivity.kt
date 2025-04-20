package com.example.ppt_munic.pantallas.albumComercio

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.albumComercio.AlbumCache
import com.example.ppt_munic.data.albumComercio.AlbumComercioAdapter
import com.example.ppt_munic.data.albumComercio.AlbumComercioRespuesta
import com.example.ppt_munic.data.categoria.CategoriaSeleccionada
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.categoria.AsignarImagenCategoria
import com.example.ppt_munic.pantallas.menu.DrawerActivity
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

        adapter = AlbumComercioAdapter(mutableListOf()) { base64String ->
            abrirImagenCompleta(base64String)
        }
        recyclerAlbum.adapter = adapter

        val precargado = AlbumCache.cache[comercioId]
        if (precargado != null) {
            adapter.actualizarLista(precargado)
        } else {
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
                    AlbumCache.cache[comercioId] = albumList
                }
            }

            override fun onFailure(call: Call<AlbumComercioRespuesta>, t: Throwable) {
                // Error silenciado en release
            }
        })
    }

    private fun abrirImagenCompleta(base64String: String) {
        try {
            val base64Clean = base64String.substringAfter(",")
            val imageBytes = Base64.decode(base64Clean, Base64.DEFAULT)
            val file = File.createTempFile("imagen_temp", ".jpg", cacheDir)
            file.writeBytes(imageBytes)

            val intent = Intent(this, ImagenCompletaActivity::class.java)
            intent.putExtra("pathImagen", file.absolutePath)
            startActivity(intent)
        } catch (_: Exception) {
            // Error silenciado en release
        }
    }
}
