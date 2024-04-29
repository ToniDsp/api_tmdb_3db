package com.example.toolbar_edicion2.activitys
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toolbar_edicion2.MetodosConViewModel.PeliculasFunc
import com.example.toolbar_edicion2.R
import com.example.toolbar_edicion2.adapters.AdapterPeliculas
import com.example.toolbar_edicion2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PeliculasFunc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.btnInicio
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

        setupRvNovedades()
        setupRvMasVotadas()
        setupRvPopulares()
        setupRvProximamente()
    }

    private fun setupRvNovedades() {
        val adapterNovedades = AdapterPeliculas(this, arrayListOf())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNovedades.layoutManager = layoutManager
        binding.rvNovedades.adapter = adapterNovedades

        viewModel.listaNovedades.observe(this) { peliculas ->
            adapterNovedades.listaPeliculas = peliculas
            adapterNovedades.notifyDataSetChanged()
        }

        viewModel.obtenerNovedades()
    }

    private fun setupRvMasVotadas() {
        val adapterMasVotadas = AdapterPeliculas(this, arrayListOf())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMasVotadas.layoutManager = layoutManager
        binding.rvMasVotadas.adapter = adapterMasVotadas

        viewModel.listaMasVotadas.observe(this) { peliculas ->
            adapterMasVotadas.listaPeliculas = peliculas
            adapterMasVotadas.notifyDataSetChanged()
        }

        viewModel.obtenerMasVotadas()
    }

    private fun setupRvPopulares() {
        val adapterPopulares = AdapterPeliculas(this, arrayListOf())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopulares.layoutManager = layoutManager
        binding.rvPopulares.adapter = adapterPopulares

        viewModel.listaPopulares.observe(this) { peliculas ->
            adapterPopulares.listaPeliculas = peliculas
            adapterPopulares.notifyDataSetChanged()
        }

        viewModel.obtenerPopulares()
    }

    private fun setupRvProximamente() {
        val adapterProximamente = AdapterPeliculas(this, arrayListOf())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCartelera.layoutManager = layoutManager
        binding.rvCartelera.adapter = adapterProximamente

        viewModel.listaProximamente.observe(this) { peliculas ->
            adapterProximamente.listaPeliculas = peliculas
            adapterProximamente.notifyDataSetChanged()
        }

        viewModel.obtenerProximamente()
    }
}