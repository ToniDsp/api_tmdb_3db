package com.example.toolbar_edicion2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toolbar_edicion2.R
import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas

class AdapterBusqueda (
    val context: Context,
    var listaPeliculas: List<InfoPeliculas>,
    private val itemClickListener: OnItemClickListener
): RecyclerView.Adapter<AdapterBusqueda.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cvPeliculas = itemView.findViewById(R.id.cvPelicula) as CardView
        val ivPoster = itemView.findViewById(R.id.ivPoster) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_peliculas, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listaPeliculas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pelicula = listaPeliculas[position]
        Glide
            .with(context)
            .load("${Constantes.BASE_URL_IMAGEN}${pelicula.poster}")
            .apply(RequestOptions()
                .override(500, 750) // Tamaño de la imagen
                .placeholder(R.drawable.imga) // Placeholder con el mismo tamaño
            )
            .into(holder.ivPoster)
        holder.cvPeliculas.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }
}