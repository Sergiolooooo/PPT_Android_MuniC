package com.example.ppt_munic.data.Productos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.categoria.AsignarImagenCategoria

class ProductosAdapter(
    private var productos: List<Producto>,
    private val imagenBase64: String?
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombre.text = producto.nombre_producto
        holder.descripcion.text = producto.descripcion_producto
        holder.precio.text = "₡${producto.precio}"

        // 🔹 Mostrar imagen dinámica usando Glide
        AsignarImagenCategoria.cargar(holder.itemView.context, imagenBase64, holder.imagen)
    }

    override fun getItemCount(): Int = productos.size

    fun actualizarLista(nuevosProductos: List<Producto>) {
        productos = nuevosProductos
        notifyDataSetChanged()
    }

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcionProducto)
        val precio: TextView = itemView.findViewById(R.id.tvPrecioProducto)
        val imagen: ImageView = itemView.findViewById(R.id.imgProducto)
    }
}
