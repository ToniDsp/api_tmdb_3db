package com.example.toolbar_edicion2.respuesta

import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.api.endpointfix
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBusqueda {
    val endPointAPI: endpointfix by lazy {
        Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(endpointfix::class.java)
    }
}