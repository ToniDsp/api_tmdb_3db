package com.example.toolbar_edicion2.infoPeliculas

import android.os.Parcelable
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
    var tipoPelicula:String
)  : Serializable {
    val title: String
        get() = nombrePelicula ?: titulo ?: ""
}