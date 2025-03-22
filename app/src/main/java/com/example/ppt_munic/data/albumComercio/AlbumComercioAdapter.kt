package com.example.ppt_munic.data.albumComercio

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R

class AlbumComercioAdapter(
    private var albumList: MutableList<albumComercio>,
    private val onImageClick: (ByteArray) -> Unit
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
        val byteArray = item.datos_imagen.data.map { it.toByte() }.toByteArray()

        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        holder.imageItem.setImageBitmap(bitmap)

        holder.imageItem.setOnClickListener {
            onImageClick(byteArray)
        }
    }

    override fun getItemCount(): Int = albumList.size

    fun actualizarLista(nuevaLista: List<albumComercio>) {
        albumList.clear()
        albumList.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
