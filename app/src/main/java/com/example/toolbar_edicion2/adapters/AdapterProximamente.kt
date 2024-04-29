package com.example.toolbar_edicion2.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toolbar_edicion2.R
import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas

class AdapterProximamente(
    val context: Context,
    var listaPeliculas: List<InfoPeliculas>
): RecyclerView.Adapter<AdapterProximamente.ViewHolder>() {

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
            .apply(RequestOptions().override(Constantes.IMAGEN_ANCHO, Constantes.IMAGEN_ALTO))
            .into(holder.ivPoster)
    }
}
