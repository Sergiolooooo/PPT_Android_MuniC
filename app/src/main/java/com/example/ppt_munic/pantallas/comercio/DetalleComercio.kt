package com.example.ppt_munic.pantallas.comercio

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ppt_munic.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleComercio : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comercio)

        // Asignar vistas
        val iconoComercio: ImageView = findViewById(R.id.iconoComercio)
        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)
        val webView: WebView = findViewById(R.id.webViewVideo)
        mapView = findViewById(R.id.mapView)

        // Recibir los datos del intent
        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val telefono = intent.getIntExtra("telefono", 0).toString()
        val videoUrl = intent.getStringExtra("video_youtube")

        // Intentar obtener latitud y longitud, si no existen, usar coordenadas por defecto
        val latitud = intent.getStringExtra("latitud")?.toDoubleOrNull() ?: 10.426869410231593
        val longitud = intent.getStringExtra("longitud")?.toDoubleOrNull() ?: -85.09165648252866

        // Asignar valores
        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        tvTelefono.text = "Teléfono: $telefono"

        // Inicializar MapView en un hilo separado
        lifecycleScope.launch(Dispatchers.Main) {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this@DetalleComercio)
        }

        // Configuración para evitar que WebView acceda a la cámara
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.mediaPlaybackRequiresUserGesture = false

        // Cargar el video si la URL es válida
        if (!videoUrl.isNullOrEmpty() && videoUrl.contains("youtube.com")) {
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadUrl(videoUrl)
            webView.visibility = WebView.VISIBLE
        } else {
            webView.visibility = WebView.GONE
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Obtener latitud y longitud con la opción por defecto
        val latitud = intent.getStringExtra("latitud")?.toDoubleOrNull() ?: 10.426869410231593
        val longitud = intent.getStringExtra("longitud")?.toDoubleOrNull() ?: -85.09165648252866
        val ubicacion = LatLng(latitud, longitud)

        googleMap?.addMarker(MarkerOptions().position(ubicacion).title("Ubicación del comercio"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
