package com.example.toolbar_edicion2.respuesta

import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas
import com.google.gson.annotations.SerializedName

data class RespPeliculas(
    @SerializedName("results")
    var resultados: List<InfoPeliculas>
)
