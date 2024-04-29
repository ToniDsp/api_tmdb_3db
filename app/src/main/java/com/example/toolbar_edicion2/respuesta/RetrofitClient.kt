package com.example.toolbar_edicion2.respuesta

import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.api.endpointfix
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    val endPointAPI: endpointfix by lazy {
        Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(endpointfix::class.java) // Cambia esto por endPointAPIImpl o endPointAPIImpl::class.java
    }
}