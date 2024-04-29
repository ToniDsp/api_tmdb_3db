package com.example.toolbar_edicion2.MetodosConViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas
import com.example.toolbar_edicion2.respuesta.RespPeliculas
import com.example.toolbar_edicion2.respuesta.RetrofitBusqueda
import com.example.toolbar_edicion2.respuesta.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PeliculasFunc: ViewModel() {
    //Lista novedades
    private val _listaNovedades = MutableLiveData<List<InfoPeliculas>>()
    val listaNovedades: LiveData<List<InfoPeliculas>> get() = _listaNovedades

    //Lista MasVotadas
    private val _listaMasVotadas = MutableLiveData<List<InfoPeliculas>>()
    val listaMasVotadas: LiveData<List<InfoPeliculas>> get() = _listaMasVotadas

    //Lista listaPopulares
    private val _listaPopulares = MutableLiveData<List<InfoPeliculas>>()
    val listaPopulares: LiveData<List<InfoPeliculas>> get() = _listaPopulares

    //lista listaProximamente
    private val _listaProximamente = MutableLiveData<List<InfoPeliculas>>()
    val listaProximamente: LiveData<List<InfoPeliculas>> get() = _listaProximamente

    //lista busquedaPelicula
    private val _listaBusquedaPelicula = MutableLiveData<List<InfoPeliculas>>()
    val listaBusquedaPelicula: LiveData<List<InfoPeliculas>> get() = _listaBusquedaPelicula


    //Lista se puede modificar (Permite actualizar la lista)
    var _listaPelicula = MutableLiveData<List<InfoPeliculas>>()

    //Lista que no se puede modificar (Utilizo para vistas)
    val listaPelicula: LiveData<List<InfoPeliculas>> = _listaPelicula

    fun obtenerProximamente() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = RetrofitClient.endPointAPI.obtenerProximamente(Constantes.API_KEY)
            withContext(Dispatchers.Main) {
                _listaProximamente.value = response.body()?.resultados
            }
        }
    }

    fun obtenerNovedades() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.endPointAPI.obtenerNovedades(Constantes.API_KEY)
            withContext(Dispatchers.Main) {
                _listaNovedades.value = response.body()?.resultados
            }
        }
    }

    fun obtenerMasVotadas() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.endPointAPI.obtenerMasVotadas(Constantes.API_KEY)
            withContext(Dispatchers.Main) {
                _listaMasVotadas.value = response.body()?.resultados
            }
        }
    }

    fun obtenerPopulares() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.endPointAPI.obtenerPopulares(Constantes.API_KEY)
            withContext(Dispatchers.Main) {
                _listaPopulares.value = response.body()?.resultados
            }
        }
    }

    fun bbuscarPelicula(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = realizarBusquedaPelicula(query)
            withContext(Dispatchers.Main) {
                _listaBusquedaPelicula.value = response.body()?.resultados
            }
        }
    }

    suspend fun realizarBusquedaPelicula(query: String): Response<RespPeliculas> {
        return RetrofitBusqueda.endPointAPI.busquedaPelicula(Constantes.API_KEY, query)
    }
}