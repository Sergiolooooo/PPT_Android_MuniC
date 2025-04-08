package com.example.ppt_munic.pantallas.producto

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.Productos.ProductosAdapter
import com.example.ppt_munic.data.Productos.ProductosRespuesta
import com.example.ppt_munic.data.categoria.CategoriaSeleccionada
import com.example.ppt_munic.pantallas.categoria.AsignarImagenCategoria
import com.example.ppt_munic.network.RetrofitClient
import com.example.ppt_munic.pantallas.menu.DrawerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductosActivity : DrawerActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductosAdapter
    private lateinit var btnCerrar: ImageView
    private lateinit var iconoComercio: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        recyclerView = findViewById(R.id.recyclerProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoComercio = findViewById(R.id.iconoComercio)

        val idComercio = intent.getIntExtra("id_comercio", 0)
        val imagenBase64 = CategoriaSeleccionada.imagen // 🔹 Recuperar imagen base64

        // 🔹 Cargar imagen base64
        AsignarImagenCategoria.cargar(this, imagenBase64, iconoComercio)

        // 🔹 Pasar imagen base64 al adapter
        adapter = ProductosAdapter(emptyList(), imagenBase64)
        recyclerView.adapter = adapter

        obtenerProductos(idComercio)

        btnCerrar.setOnClickListener {
            finish()
        }
    }

    private fun obtenerProductos(comercioId: Int) {
        val apiService = RetrofitClient.api

        Log.d("ProductosActivity", "Llamando a la API con comercioId: $comercioId")

        apiService.getProductosByComercio(comercioId).enqueue(object : Callback<ProductosRespuesta> {
            override fun onResponse(call: Call<ProductosRespuesta>, response: Response<ProductosRespuesta>) {
                Log.d("ProductosActivity", "URL de la petición: ${call.request().url}")

                if (response.isSuccessful) {
                    response.body()?.let { productosRespuesta ->
                        Log.d("ProductosActivity", "Productos recibidos: ${productosRespuesta.data.size}")
                        adapter.actualizarLista(productosRespuesta.data)
                    }
                } else {
                    Log.e("ProductosActivity", "Error en la respuesta: Código HTTP ${response.code()}")
                    Log.e("ProductosActivity", "Error Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductosRespuesta>, t: Throwable) {
                Log.e("ProductosActivity", "Error en la API: ${t.message}")
            }
        })
    }
}
