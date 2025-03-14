package com.example.ppt_munic.data.categoria

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.comercio.ComerciosActivity

class CategoriaAdapter(
    private var categorias: List<Categoria>
) : RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoria)
        val iconoCategoria: ImageView = view.findViewById(R.id.iconoCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.tvCategoria.text = categoria.nombre

        // Asignar icono usando la función de AsignarIconos
        holder.iconoCategoria.setImageResource(AsignarIconos.obtenerIconoPorCategoria(categoria.nombre))

        // Asegurar tamaño y escalado correcto del icono
        holder.iconoCategoria.layoutParams.width = 200
        holder.iconoCategoria.layoutParams.height = 200
        holder.iconoCategoria.scaleType = ImageView.ScaleType.FIT_CENTER

        // Agregar listener de clic para abrir comercios filtrados por categoría
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ComerciosActivity::class.java).apply {
                putExtra("categoria", categoria.nombre)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = categorias.size

    fun actualizarLista(nuevaLista: List<Categoria>) {
        categorias = nuevaLista
        notifyDataSetChanged()
    }
}
