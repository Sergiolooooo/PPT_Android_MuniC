package com.example.ppt_munic.data

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.DetalleComercio


class ComercioAdapter(private val comercios: List<Comercio>) :
    RecyclerView.Adapter<ComercioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comercio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comercio = comercios[position]
        holder.tvNombre.text = comercio.nombre
        holder.tvDescripcion.text = comercio.descripcion

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