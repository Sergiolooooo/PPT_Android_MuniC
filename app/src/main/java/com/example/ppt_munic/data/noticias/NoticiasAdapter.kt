package com.example.ppt_munic.ui.noticias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.data.noticias.Noticia

class NoticiasAdapter(
    private val noticias: MutableList<Noticia>,
    private val onClick: (Noticia) -> Unit
) : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {

    inner class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView = itemView.findViewById(R.id.txtTitulo)
        private val fecha: TextView = itemView.findViewById(R.id.txtFecha)
        private val icono: ImageView = itemView.findViewById(R.id.imgIcono)

        fun bind(noticia: Noticia) {
            titulo.text = noticia.titulo
            fecha.text = noticia.fecha_publicacion.take(10)
            icono.setImageResource(R.drawable.ic_default)

            itemView.setOnClickListener { onClick(noticia) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        holder.bind(noticias[position])
    }

    override fun getItemCount(): Int = noticias.size

    fun actualizarLista(nuevaLista: List<Noticia>) {
        noticias.clear()
        noticias.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
