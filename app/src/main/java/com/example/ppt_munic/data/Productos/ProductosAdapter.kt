package com.example.ppt_munic.data.Productos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ppt_munic.R

class ProductosAdapter(private var productos: List<Producto>) :
    RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombre.text = producto.nombre_producto
        holder.descripcion.text = producto.descripcion_producto // ✅ Añadida descripción
        holder.precio.text = "₡${producto.precio}"

        // Cargar imagen del producto (placeholder si no tiene)
        Glide.with(holder.itemView.context)
            .load(R.drawable.ic_default)
            .into(holder.imagen)
    }

    override fun getItemCount(): Int = productos.size

    fun actualizarLista(nuevosProductos: List<Producto>) {
        productos = nuevosProductos
        notifyDataSetChanged()
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcionProducto) // ✅ Nuevo campo
        val precio: TextView = itemView.findViewById(R.id.tvPrecioProducto)
        val imagen: ImageView = itemView.findViewById(R.id.imgProducto)
    }
}
