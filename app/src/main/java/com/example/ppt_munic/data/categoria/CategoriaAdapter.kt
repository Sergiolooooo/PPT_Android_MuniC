package com.example.ppt_munic.data.categoria

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.comercio.ComerciosActivity

class CategoriaAdapter(
    private val categorias: List<Categoria>
) : RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.tvCategoria.text = categoria.nombre

        holder.itemView.setOnClickListener {
            // Al hacer click, lanzamos la pantalla de comercios filtrados por categoría.
            val intent = Intent(holder.itemView.context, ComerciosActivity::class.java).apply {
                putExtra("categoria", categoria.nombre)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = categorias.size
}