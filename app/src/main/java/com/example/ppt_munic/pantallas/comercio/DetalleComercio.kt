package com.example.ppt_munic.pantallas.comercio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
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
    private lateinit var btnCerrar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comercio)

        val iconoComercio: ImageView = findViewById(R.id.iconoComercio)
        val tvNombre: TextView = findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = findViewById(R.id.tvDescripcion)
        val tvTelefono: TextView = findViewById(R.id.tvTelefono)
        val tvVideoYoutube: TextView = findViewById(R.id.tvVideoYoutube) // Nuevo texto para el video
        val thumbnailYouTube: ImageView = findViewById(R.id.thumbnailYouTube)
        mapView = findViewById(R.id.mapView)
        btnCerrar = findViewById(R.id.btn_cerrar)

        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val telefono = intent.getStringExtra("telefono")
        val videoUrl = intent.getStringExtra("video_youtube")
        val iconoRes = intent.getIntExtra("iconoCategoria", R.drawable.ic_default)

        // Asignar valores a los TextView
        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        tvTelefono.text = if (!telefono.isNullOrEmpty() && telefono != "null") "Teléfono: $telefono" else "Teléfono: No disponible"
        tvVideoYoutube.text = "Video de YouTube" // Agregamos el texto sobre la miniatura

        // Cargar icono de la categoría seleccionado
        Glide.with(this)
            .load(iconoRes)
            .placeholder(R.drawable.ic_default)
            .into(iconoComercio)

        // Inicializar el mapa de manera asíncrona
        lifecycleScope.launch(Dispatchers.Main) {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this@DetalleComercio)
        }

        // Cargar miniatura de YouTube
        if (!videoUrl.isNullOrEmpty() && videoUrl.contains("youtube.com")) {
            val videoId = getYouTubeVideoId(videoUrl)
            val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"

            Glide.with(this)
                .load(thumbnailUrl)
                .placeholder(R.drawable.img_youtube_default)
                .into(thumbnailYouTube)

            thumbnailYouTube.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }
        } else {
            Glide.with(this)
                .load(R.drawable.img_youtube_default)
                .into(thumbnailYouTube)
        }

        // Cerrar la actividad cuando se presiona el botón de cerrar
        btnCerrar.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        // Obtener la latitud y longitud pasadas desde el Intent
        val latitud = intent.getDoubleExtra("latitud", 10.426869) // Si no hay datos, usa el valor por defecto
        val longitud = intent.getDoubleExtra("longitud", -85.091656)
        val ubicacion = LatLng(latitud, longitud)

        // Mueve los controles de Google Maps (logo y botón de navegación)
        googleMap?.setPadding(0, 100, 0, 100) // Ajusta el logo más arriba
        googleMap?.uiSettings?.isMapToolbarEnabled = true // Asegura que el botón de navegación esté visible

        // Agrega marcador en la ubicación del comercio
        googleMap?.addMarker(MarkerOptions().position(ubicacion).title("Ubicación del comercio"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
    }
    
    private fun getYouTubeVideoId(videoUrl: String): String {
        val regex = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|%2Fv%2F|e%2F|watch\\?v%3D|watch\\?feature=player_embedded&v%3D|youtu.be%2F|%2Fv%2F|%2Fembed%2F|%2Fe%2F|%2Fwatch\\?v%3D|%2Fwatch\\?feature=player_embedded&v%3D|%2Fvideos%2F|%2Fembed%2F|%2Fe%2F|%2Fv%2F|youtu\\.be/|v%3D)([a-zA-Z0-9_-]{11})".toRegex()
        val matchResult = regex.find(videoUrl)
        return matchResult?.groups?.get(1)?.value ?: ""
    }
}
