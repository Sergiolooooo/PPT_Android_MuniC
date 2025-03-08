package com.example.ppt_munic.pantallas.comercio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ppt_munic.R
import com.example.ppt_munic.data.Redes_Sociales.RedesSocialesAdapter
import com.example.ppt_munic.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class DetalleComercio : AppCompatActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var rvRedesSociales: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comercio)

        // 🔹 Inicializar vistas
        val iconoComercio: ImageView = findViewById(R.id.iconoComercio)
        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)
        val btnGoogle: ImageView = findViewById(R.id.btnGoogle)
        rvRedesSociales = findViewById(R.id.rvRedesSociales)
        btnCerrar = findViewById(R.id.btn_cerrar)

        // 🔹 Asegurar que el botón `Cerrar` reciba clics correctamente
        btnCerrar.isClickable = true
        btnCerrar.isFocusable = true
        btnCerrar.bringToFront() // 🔹 Asegura que el botón esté en la capa superior

        // 🔹 Configuración de botón de cierre con depuración
        btnCerrar.setOnClickListener {
            Log.d("DetalleComercio", "Botón cerrar presionado")
            finish()
        }

        // 🔹 Obtener datos del intent
        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val telefono = intent.getStringExtra("telefono")
        val videoUrl = intent.getStringExtra("video_youtube") ?: "Sin video"
        val urlGoogle = intent.getStringExtra("url_google") ?: "Sin enlace"
        val iconoRes = intent.getIntExtra("iconoCategoria", R.drawable.ic_default)
        val comercioId = intent.getIntExtra("id_comercio", -1)

        Log.d("TEST_VIDEO", "URL de YouTube recibida: $videoUrl")
        Log.d("TEST_COMERCIO", "ID del comercio: $comercioId")

        // 🔹 Asignar datos a los elementos de la UI
        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        tvTelefono.text = if (!telefono.isNullOrEmpty() && telefono != "null") "Teléfono: $telefono" else "Teléfono: No disponible"

        Glide.with(this)
            .load(iconoRes)
            .placeholder(R.drawable.ic_default)
            .into(iconoComercio)

        // 🔹 Verificar y mostrar botón de Google Maps si la URL es válida
        if (isValidGoogleUrl(urlGoogle)) {
            btnGoogle.visibility = View.VISIBLE
            btnGoogle.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlGoogle))
                startActivity(intent)
            }
        } else {
            btnGoogle.visibility = View.GONE
        }

        // 🔹 Obtener redes sociales si hay un comercio válido
        if (comercioId != -1) {
            obtenerRedesSociales(comercioId, videoUrl)
        }

        Log.d("TEST_LOG", "Iniciando DetalleComercio")
    }

    private fun obtenerRedesSociales(comercioId: Int, videoUrl: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getRedesByComercio(comercioId).execute()
                Log.d("API_RESPONSE", "Código HTTP: ${response.code()}")
                Log.d("API_RESPONSE", "Respuesta completa: ${response.body()}")

                if (response.isSuccessful) {
                    val redesSociales = response.body()?.data ?: emptyList()
                    Log.d("API_RESPONSE", "Redes obtenidas: ${redesSociales.size}")

                    withContext(Dispatchers.Main) {
                        val totalItems = redesSociales.size + if (videoUrl != "Sin video") 1 else 0

                        // 🔹 Configurar diseño de RecyclerView con FlexboxLayoutManager
                        val flexboxLayoutManager = FlexboxLayoutManager(this@DetalleComercio)
                        flexboxLayoutManager.justifyContent = JustifyContent.CENTER

                        rvRedesSociales.layoutManager = flexboxLayoutManager
                        rvRedesSociales.adapter = RedesSocialesAdapter(redesSociales,
                            if (videoUrl != "Sin video") videoUrl else null, this@DetalleComercio)
                        rvRedesSociales.adapter?.notifyDataSetChanged()
                    }
                } else {
                    Log.e("API_ERROR", "Error en respuesta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error en la llamada: ${e.message}")
            }
        }
    }

    private fun isValidGoogleUrl(url: String): Boolean {
        return url.isNotEmpty() && url != "Sin enlace" &&
                (url.startsWith("http://") || url.startsWith("https://"))
    }
}
