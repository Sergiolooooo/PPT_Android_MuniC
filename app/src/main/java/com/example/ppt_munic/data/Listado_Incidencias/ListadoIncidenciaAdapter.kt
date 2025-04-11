package com.example.ppt_munic.data.Listado_Incidencias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R

class ListadoIncidenciaAdapter(
    private var lista: List<ListadoIncidencia>,
    private val onItemClick: (ListadoIncidencia) -> Unit
) : RecyclerView.Adapter<ListadoIncidenciaAdapter.IncidenciaViewHolder>() {

    private var listaCompleta: List<ListadoIncidencia> = lista.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidenciaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_listado_incidencia, parent, false)
        return IncidenciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncidenciaViewHolder, position: Int) {
        val item = lista[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<ListadoIncidencia>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    fun setListaCompleta(original: List<ListadoIncidencia>) {
        listaCompleta = original
        actualizarLista(original)
    }

    fun filtrar(query: String) {
        val filtro = query.lowercase().trim()
        if (filtro.isEmpty()) {
            actualizarLista(listaCompleta)
        } else {
            val filtrada = listaCompleta.filter {
                it.nombre_incidencia.lowercase().contains(filtro) ||
                        it.descripcion_incidencia.lowercase().contains(filtro)
            }
            actualizarLista(filtrada)
        }
    }

    class IncidenciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgIncidencia: ImageView = itemView.findViewById(R.id.imgIncidencia)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreIncidencia)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionIncidencia)

        fun bind(incidencia: ListadoIncidencia) {
            imgIncidencia.setImageResource(R.drawable.ic_default)
            tvNombre.text = incidencia.nombre_incidencia
            tvDescripcion.text = incidencia.descripcion_incidencia
        }
    }
}
