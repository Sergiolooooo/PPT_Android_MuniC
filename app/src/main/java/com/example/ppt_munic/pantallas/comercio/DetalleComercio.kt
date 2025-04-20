package com.example.ppt_munic.pantallas.comercio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.redes_Sociales.RedesSocialesAdapter
import com.example.ppt_munic.data.albumComercio.AlbumCache
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.albumComercio.AlbumComercioActivity
import com.example.ppt_munic.pantallas.categoria.AsignarImagenCategoria
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import com.example.ppt_munic.pantallas.producto.ProductosActivity
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleComercio : DrawerActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var btnProductos: ImageView
    private lateinit var btnAlbum: ImageView
    private lateinit var rvRedesSociales: RecyclerView

    private var comercioId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comercio)

        val iconoComercio: ImageView = findViewById(R.id.iconoComercio)
        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)
        val btnGoogle: ImageView = findViewById(R.id.btnGoogle)
        rvRedesSociales = findViewById(R.id.rvRedesSociales)
        btnCerrar = findViewById(R.id.btn_cerrar)
        btnProductos = findViewById(R.id.btnProductos)
        btnAlbum = findViewById(R.id.btnAlbum)

        btnCerrar.setOnClickListener {
            finish()
        }

        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val telefono = intent.getStringExtra("telefono")
        val videoUrl = intent.getStringExtra("video_youtube") ?: "Sin video"
        val urlGoogle = intent.getStringExtra("url_google") ?: "Sin enlace"
        val imagenBase64 = com.example.ppt_munic.data.categoria.CategoriaSeleccionada.imagen
        comercioId = intent.getIntExtra("id_comercio", -1)

        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        tvTelefono.text = if (!telefono.isNullOrEmpty() && telefono != "null") {
            "Teléfono: $telefono"
        } else {
            "Teléfono: No disponible"
        }

        AsignarImagenCategoria.cargar(this, imagenBase64, iconoComercio)

        if (isValidGoogleUrl(urlGoogle)) {
            btnGoogle.visibility = View.VISIBLE
            btnGoogle.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlGoogle))
                startActivity(intent)
            }
        } else {
            btnGoogle.visibility = View.GONE
        }

        if (comercioId != -1) {
            obtenerRedesSociales(comercioId, videoUrl)
            precargarAlbum(comercioId)
        }

        btnProductos.setOnClickListener {
            val intent = Intent(this, ProductosActivity::class.java)
            intent.putExtra("id_comercio", comercioId)
            startActivity(intent)
        }

        btnAlbum.setOnClickListener {
            val intent = Intent(this, AlbumComercioActivity::class.java)
            intent.putExtra("id_comercio", comercioId)
            startActivity(intent)
        }
    }

    private fun precargarAlbum(comercioId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getAlbumByComercio(comercioId).execute()
                if (response.isSuccessful && response.body()?.success == true) {
                    val albumList = response.body()?.data ?: emptyList()
                    AlbumCache.cache[comercioId] = albumList
                }
            } catch (_: Exception) {
                // Error silenciado en release
            }
        }
    }

    private fun obtenerRedesSociales(comercioId: Int, videoUrl: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getRedesByComercio(comercioId).execute()
                if (response.isSuccessful) {
                    val redesSociales = response.body()?.data ?: emptyList()
                    withContext(Dispatchers.Main) {
                        val flexboxLayoutManager = FlexboxLayoutManager(this@DetalleComercio)
                        flexboxLayoutManager.justifyContent = JustifyContent.CENTER
                        rvRedesSociales.layoutManager = flexboxLayoutManager
                        rvRedesSociales.adapter = RedesSocialesAdapter(
                            redesSociales,
                            if (videoUrl != "Sin video") videoUrl else null,
                            this@DetalleComercio
                        )
                        rvRedesSociales.adapter?.notifyDataSetChanged()
                    }
                }
            } catch (_: Exception) {
                // Error silenciado en release
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AlbumCache.cache.remove(comercioId)
    }

    private fun isValidGoogleUrl(url: String): Boolean {
        return url.isNotEmpty() &&
                url != "Sin enlace" &&
                (url.startsWith("http://") || url.startsWith("https://"))
    }
}
