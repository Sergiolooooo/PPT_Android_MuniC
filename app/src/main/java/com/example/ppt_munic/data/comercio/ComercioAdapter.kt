package com.example.ppt_munic.data.comercio

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.categoria.CategoriaSeleccionada
import com.example.ppt_munic.pantallas.categoria.AsignarImagenCategoria
import com.example.ppt_munic.pantallas.comercio.DetalleComercio

class ComercioAdapter(
    private var comercios: List<Comercio>,
    private val imagenBase64: String?
) : RecyclerView.Adapter<ComercioAdapter.ViewHolder>() {

    private var listaCompleta: List<Comercio> = comercios.toList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgComercio: ImageView = view.findViewById(R.id.imgComercio)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreComercio)
        val tvTelefono: TextView = view.findViewById(R.id.tvTelefonoComercio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comercio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comercio = comercios[position]
        holder.tvNombre.text = comercio.nombre
        holder.tvTelefono.text = comercio.telefono.toString()

        AsignarImagenCategoria.cargar(holder.itemView.context, imagenBase64, holder.imgComercio)

        holder.itemView.setOnClickListener {
            CategoriaSeleccionada.imagen = imagenBase64
            val intent = Intent(holder.itemView.context, DetalleComercio::class.java).apply {
                putExtra("id_comercio", comercio.id)
                putExtra("nombre", comercio.nombre)
                putExtra("descripcion", comercio.descripcion)
                putExtra("url_google", comercio.google)
                putExtra("telefono", comercio.telefono)
                putExtra("video_youtube", comercio.videoYoutube)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = comercios.size

    fun actualizarLista(nuevaLista: List<Comercio>) {
        comercios = nuevaLista
        notifyDataSetChanged()
    }

    fun setListaCompleta(original: List<Comercio>) {
        listaCompleta = original
        actualizarLista(original)
    }

    fun filtrar(query: String) {
        val filtro = query.lowercase().trim()
        if (filtro.isEmpty()) {
            actualizarLista(listaCompleta)
        } else {
            val filtrada = listaCompleta.filter {
                it.nombre.lowercase().contains(filtro) ||
                        it.descripcion.lowercase().contains(filtro) ||
                        it.telefono.toString().contains(filtro)
            }
            actualizarLista(filtrada)
        }
    }
}
