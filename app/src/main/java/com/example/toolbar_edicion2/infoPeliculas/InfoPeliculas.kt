package com.example.toolbar_edicion2.infoPeliculas

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class InfoPeliculas(
    @SerializedName("id")
    var id:String,
    @SerializedName("name")
    var nombrePelicula: String? = null,
    @SerializedName("title")
    var titulo: String? = null,
    @SerializedName("overview")
    var descripcion: String,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("release_date")
    var fechaLanzamiento:String,
    @SerializedName("vote_average")
    var votoPromedio:String,
    @SerializedName("media_type")
    var tipoPelicula:String,
    @SerializedName("genre_ids")
    var generos: List <Int>,

)  : Serializable {
    val title: String
        get() = nombrePelicula ?: titulo ?: ""
}