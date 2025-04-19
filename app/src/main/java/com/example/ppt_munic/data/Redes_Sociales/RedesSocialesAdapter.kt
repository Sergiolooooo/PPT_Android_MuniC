package com.example.ppt_munic.data.Redes_Sociales

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R

class RedesSocialesAdapter(
    private val redesSociales: List<RedesSociales>,
    private val youtubeUrl: String?,
    private val context: Context
) : RecyclerView.Adapter<RedesSocialesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgRedSocial: ImageView = view.findViewById(R.id.imgRedSocial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_red_social, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = if (position < redesSociales.size) redesSociales[position] else null

        if (item != null) {
            Log.d("BIND", "Asignando red social: ${item.nombre_red_social}")

            when (item.nombre_red_social.lowercase()) {
                "facebook" -> holder.imgRedSocial.setImageResource(R.drawable.ic_facebook)
                "twitter" -> holder.imgRedSocial.setImageResource(R.drawable.ic_twitter_x)
                "x" -> holder.imgRedSocial.setImageResource(R.drawable.ic_twitter_x)
                "instagram" -> holder.imgRedSocial.setImageResource(R.drawable.ic_instagram)
                "whatsapp" -> holder.imgRedSocial.setImageResource(R.drawable.ic_whatsapp)
                else -> holder.imgRedSocial.setImageResource(R.drawable.ic_default)
            }

            holder.imgRedSocial.layoutParams.width = 150
            holder.imgRedSocial.layoutParams.height = 150
            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.enlace))
                context.startActivity(intent)
            }
        } else if (!youtubeUrl.isNullOrEmpty()) {
            Log.d("BIND", "Agregando YouTube")

            holder.imgRedSocial.setImageResource(R.drawable.ic_youtubess)
            holder.imgRedSocial.layoutParams.width = 150
            holder.imgRedSocial.layoutParams.height = 150
            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                context.startActivity(intent)
            }
        }
    }



    override fun getItemCount(): Int {
        Log.d("ADAPTER", "Cantidad de redes + YouTube: ${redesSociales.size + if (!youtubeUrl.isNullOrEmpty()) 1 else 0}")
        return redesSociales.size + if (!youtubeUrl.isNullOrEmpty()) 1 else 0
    }

}
