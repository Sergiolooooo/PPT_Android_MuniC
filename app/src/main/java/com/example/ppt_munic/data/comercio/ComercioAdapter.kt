package com.example.ppt_munic.data.comercio

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.comercio.DetalleComercio

class ComercioAdapter(private val comercios: List<Comercio>) :
    RecyclerView.Adapter<ComercioAdapter.ViewHolder>() {

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

        // Usar ic_default como imagen predeterminada
        holder.imgComercio.setImageResource(R.drawable.ic_default)

        // Manejo de clic para abrir la pantalla de detalles
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleComercio::class.java).apply {
                putExtra("nombre", comercio.nombre)
                putExtra("descripcion", comercio.descripcion)
                putExtra("latitud", comercio.latitud)
                putExtra("longitud", comercio.longitud)
                putExtra("telefono", comercio.telefono)
                putExtra("categoria", comercio.categoria)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = comercios.size
}
