package com.example.toolbar_edicion2.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toolbar_edicion2.R
import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas
import com.example.toolbar_edicion2.sqlite.DatabaseHelper

class FavoritosAdapter(
    private val peliculas: MutableList<InfoPeliculas>,
    private val generosPorPelicula: Map<String, String>
) : RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>() {

    inner class FavoritosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val tituloPeliculaTextView: TextView = itemView.findViewById(R.id.tituloPeliculaTextView)
        val fechaLanzamientoTextView: TextView = itemView.findViewById(R.id.fechaLanzamientoTextView)
        val votoPromedioTextView: TextView = itemView.findViewById(R.id.votoPromedioTextView)
        val cardView: CardView = itemView.findViewById(R.id.cvEstadoVisto)
        val tvGeneros: TextView = itemView.findViewById(R.id.tvGeneros)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorito, parent, false)
        return FavoritosViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val pelicula = peliculas[position]
        val generos = generosPorPelicula[pelicula.id]
        holder.tvGeneros.text = generos
        Glide.with(holder.posterImageView.context)
            .load("${Constantes.BASE_URL_IMAGEN}${pelicula.poster}")
            .into(holder.posterImageView)
        if(pelicula.tipoPelicula == "movie"){
            holder.tituloPeliculaTextView.text = pelicula.titulo
        } else if (pelicula.tipoPelicula == "tv"){
            holder.tituloPeliculaTextView.text = pelicula.nombrePelicula
        }
        holder.fechaLanzamientoTextView.text = pelicula.fechaLanzamiento
        holder.votoPromedioTextView.text = pelicula.votoPromedio

        val databaseHelper = DatabaseHelper(holder.itemView.context)
        val estado = databaseHelper.checkVisto(pelicula.id)
        if (estado) {
            holder.cardView.setCardBackgroundColor(Color.GREEN)
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE)
        }
        holder.itemView.findViewById<ImageButton>(R.id.botonOpciones).setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.boton_opciones_favoritos, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.eliminarFavorito -> {
                        databaseHelper.borrarFavorito(pelicula.id)
                        peliculas.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, peliculas.size)
                        true
                    }
                    R.id.marcarVisto -> {
                        databaseHelper.marcarComoVisto(pelicula.id)
                        holder.cardView.setCardBackgroundColor(Color.GREEN)
                        notifyItemChanged(position)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

    }

    override fun getItemCount() = peliculas.size
}