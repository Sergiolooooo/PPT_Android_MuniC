package com.example.ppt_munic.data.evento

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.evento.IconosEvento

class EventoAdapter(
    private var eventos: List<Evento>,
    private val onClick: (Evento) -> Unit
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    private var listaCompleta: List<Evento> = eventos.toList()

    inner class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView = itemView.findViewById(R.id.txtTitulo)
        private val fecha: TextView = itemView.findViewById(R.id.txtFecha)
        private val icono: ImageView = itemView.findViewById(R.id.imgIcono)

        fun bind(evento: Evento) {
            titulo.text = evento.nombre
            fecha.text = evento.fecha.take(10)
            icono.setImageResource(IconosEvento.iconoEvento)

            itemView.setOnClickListener {
                EventoCache.cache[evento.id] = evento
                onClick(evento)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

    override fun getItemCount(): Int = eventos.size

    fun actualizarLista(nuevaLista: List<Evento>) {
        eventos = nuevaLista
        notifyDataSetChanged()
    }

    fun setListaCompleta(original: List<Evento>) {
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
                        it.fecha.lowercase().contains(filtro) ||
                        it.lugar.lowercase().contains(filtro)
            }
            actualizarLista(filtrada)
        }
    }
}
