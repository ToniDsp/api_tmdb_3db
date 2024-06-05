package com.example.toolbar_edicion2.activitys

import android.content.Intent
import android.icu.text.IDNA.Info
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toolbar_edicion2.R
import com.example.toolbar_edicion2.adapters.FavoritosAdapter
import com.example.toolbar_edicion2.databinding.ActivityFavoritosBinding
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas
import com.example.toolbar_edicion2.sqlite.DatabaseHelper

class Favoritos: AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.btnFavoritos

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

        databaseHelper = DatabaseHelper(this)
        val peliculasList: MutableList<InfoPeliculas> = databaseHelper.SelectSinEstado().toMutableList()
        val generosPorPelicula = databaseHelper.obtenerNombresGenerosTodasPeliculas()
        val adapter = FavoritosAdapter(peliculasList, generosPorPelicula)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }
}