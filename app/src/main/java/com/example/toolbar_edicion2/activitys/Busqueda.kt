package com.example.toolbar_edicion2.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toolbar_edicion2.MetodosConViewModel.PeliculasFunc
import com.example.toolbar_edicion2.R
import com.example.toolbar_edicion2.adapters.AdapterBusqueda
import com.example.toolbar_edicion2.adapters.AdapterPeliculas
import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.databinding.ActivityBuscarBinding
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Busqueda: AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener, AdapterBusqueda.OnItemClickListener {
    private lateinit var binding: ActivityBuscarBinding
    private lateinit var adapterbusqueda: AdapterBusqueda
    private lateinit var viewModel: PeliculasFunc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.btnBuscar
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnBuscar -> {
                    val intent = Intent(this, Busqueda::class.java)
                    startActivity(intent)
                    true
                }
                R.id.btnInicio -> {
                    val intentMain = Intent(this, MainActivity::class.java)
                    startActivity(intentMain)
                    true
                }
                R.id.btnFavoritos -> {
                    val intentFav = Intent(this, Favoritos::class.java)
                    startActivity(intentFav)
                    true
                }
                else -> false
            }
        }
        viewModel = ViewModelProvider(this)[PeliculasFunc::class.java]
        binding.barraBusqueda.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false )
        binding.rvBusqueda.layoutManager = layoutManager
        adapterbusqueda = AdapterBusqueda(this, mutableListOf(), this)
        binding.rvBusqueda.adapter = adapterbusqueda
        }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            buscarPelicula(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    private fun buscarPelicula(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = viewModel.realizarBusquedaPelicula(query)
                if (response.isSuccessful) {
                    val movies = response.body()?.resultados?.sortedWith(compareBy<InfoPeliculas> { it.poster.isNullOrEmpty() }.thenByDescending { it.votoPromedio })
                    if (movies != null) {
                        adapterbusqueda.listaPeliculas = movies
                        runOnUiThread {
                            binding.rvBusqueda.adapter = adapterbusqueda
                            adapterbusqueda.notifyDataSetChanged()
                        }
                    }
                } else {
                    // Manejo de error cuando la respuesta no es exitosa
                    runOnUiThread {
                        Toast.makeText(this@Busqueda, "No se encuentran los parametros buscados", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                // Manejo de error cuando hay una excepción
                runOnUiThread {
                    Toast.makeText(this@Busqueda, "Error en la búsqueda: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onItemClick(position: Int) {
        val intent = Intent(this, DetallesPelicula::class.java)
        intent.putExtra("pelicula", adapterbusqueda.listaPeliculas[position])
        startActivity(intent)
    }
}