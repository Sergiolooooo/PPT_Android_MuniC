package com.example.ppt_munic.pantallas.evento

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.evento.EventoCache
import com.example.ppt_munic.pantallas.menu.DrawerActivity

class DetalleEvento : DrawerActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var iconoEncabezado: ImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvFecha: TextView
    private lateinit var tvLugar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DetalleEvento", "✅ Activity lanzada")
        setContentView(R.layout.activity_detalle_evento)

        // Vincular vistas
        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoEncabezado = findViewById(R.id.iconoComercio)
        tvNombre = findViewById(R.id.tvNombre)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvFecha = findViewById(R.id.tvFecha)
        tvLugar = findViewById(R.id.tvLugar)

        val id = intent.getIntExtra("id_evento", -1)
        Log.d("DetalleEvento", "Recibido ID de evento: $id")

        val evento = EventoCache.cache[id]

        if (evento != null) {
            Log.d("DetalleEvento", "Evento encontrado en caché: ${evento.nombre}")
            tvNombre.text = evento.nombre
            tvDescripcion.text = evento.descripcion
            tvFecha.text = evento.fecha.take(10)
            tvLugar.text = evento.lugar
            iconoEncabezado.setImageResource(IconosEvento.iconoEvento)
        } else {
            Log.e("DetalleEvento", "No se encontró el evento en caché. Cerrando.")
            finish()
        }

        btnCerrar.setOnClickListener {
            Log.d("DetalleEvento", "Cierre manual de pantalla")
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val id = intent.getIntExtra("id_evento", -1)
        Log.d("DetalleEvento", "onDestroy → eliminando evento ID $id del caché")
        EventoCache.cache.remove(id)
    }
}
