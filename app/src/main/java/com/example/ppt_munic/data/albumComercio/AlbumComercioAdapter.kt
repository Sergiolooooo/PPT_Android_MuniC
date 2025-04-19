package com.example.ppt_munic.data.albumComercio

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ppt_munic.R

class AlbumComercioAdapter(
    private var albumList: MutableList<albumComercio>,
    private val onImageClick: (String) -> Unit
) : RecyclerView.Adapter<AlbumComercioAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageItem: ImageView = itemView.findViewById(R.id.img_album)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album_imagen, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = albumList[position]

        Glide.with(holder.imageItem.context)
            .load(item.imagen) // 👈 base64 completo
            .placeholder(R.drawable.ic_default)
            .into(holder.imageItem)

        holder.imageItem.setOnClickListener {
            onImageClick(item.imagen) // 👈 Pasa string base64
        }
    }

    override fun getItemCount(): Int = albumList.size

    fun actualizarLista(nuevaLista: List<albumComercio>) {
        albumList.clear()
        albumList.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
