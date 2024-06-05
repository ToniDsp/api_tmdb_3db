package com.example.toolbar_edicion2.api

import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas
import com.example.toolbar_edicion2.respuesta.RespPeliculas
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface endpointfix {



        //Los endpoint que vamos a usar

        //   --  NOVEDADES --
        @GET("now_playing")
        suspend fun obtenerNovedades(
            @Query("api_key") apiKey: String
        ): Response<RespPeliculas>

        //   -- POPULARES --
        @GET("popular")
        suspend fun obtenerPopulares(
            @Query("api_key") apiKey: String
        ): Response<RespPeliculas>

        //  -- MAS VOTADAS --
        @GET("top_rated")
        suspend fun obtenerMasVotadas(
            @Query("api_key") apiKey: String
        ): Response<RespPeliculas>

        // -- PROXIMAMENTE --
        @GET("upcoming")
        suspend fun obtenerProximamente (
            @Query("api_key") apiKey: String
        ): Response<RespPeliculas>
        //   -- BUSCAR MULTI --
        @GET("search/multi")
        suspend fun busquedaPelicula(
            @Query("api_key") apiKey: String = Constantes.API_KEY,
            @Query("query") query: String,
            @Query("include_adult") includeAdult: Boolean = false,
            @Query("language") language: String = "en-US",
            @Query("page") page: Int = 1
        ): Response<RespPeliculas>
        //   -- BUSCAR SERIE POR ID --
        @GET("3/tv/{series_id}")
        suspend fun obtenerSerieById(
            @Path("series_id") id: Int,
            @Query("api_key") apiKey: String
        ): Response<InfoPeliculas>
        @GET("3/movie/{movie_id}")
        suspend fun obtenerPeliculaById(
            @Path("movie_id") id: Int,
            @Query("api_key") apiKey: String
        ): Response<InfoPeliculas>
    }
