package com.example.toolbar_edicion2.activitys

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.toolbar_edicion2.R
import com.example.toolbar_edicion2.api.Constantes
import com.example.toolbar_edicion2.databinding.ActivityDetallesBinding
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas
import com.example.toolbar_edicion2.sqlite.DatabaseHelper

class DetallesPelicula : AppCompatActivity(){
    private lateinit var binding: ActivityDetallesBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        val pelicula = intent.getSerializableExtra("pelicula") as? InfoPeliculas
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

        pelicula?.let {
            binding.titleTextView.text = it.title
            if (it.fechaLanzamiento == null) {
                binding.yearTextView.text = "Sin fecha de lanzamiento"
            }

            else  {
                binding.yearTextView.text = it.fechaLanzamiento
            }
            binding.noteTextView.text = it.votoPromedio?.toString()
            if (it.tipoPelicula == "tv") {
                binding.tipoPeliculaTextView.text = "Serie"
            }
            else if (it.tipoPelicula == "movie") {
                binding.tipoPeliculaTextView.text = "Pelicula"
            }
            else {
                binding.tipoPeliculaTextView.text = it.tipoPelicula
            }

            binding.synopsisTextView.text =  it.descripcion
                // Verificar si it.poster está vacío o nulo
                binding.movieImageView.apply {
                    Glide.with(this)
                        .load("${Constantes.BASE_URL_IMAGEN}${it.poster}")
                        .apply(RequestOptions()
                            .override(800, 800) // Tamaño de la imagen
                            .placeholder(R.drawable.img100x100) // Placeholder con el mismo tamaño
                        )
                        .into(this)
                }



        }
        val menuOpciones = findViewById<ImageButton>(R.id.botonOpciones)
        menuOpciones.setOnClickListener {
            val desplegable = PopupMenu(this, menuOpciones)
            desplegable.menuInflater.inflate(R.menu.boton_opciones, desplegable.menu)
            desplegable.setOnMenuItemClickListener  { menuItem ->
                when (menuItem.itemId) {
                    R.id.aniadirFavoritos -> {
                        pelicula?.let{
                            databaseHelper.insertData(
                                    it.id,
                                    it.nombrePelicula ?:"",
                                    it.titulo ?:"",
                                    it.descripcion,
                                    it.poster,
                                    it.fechaLanzamiento,
                                    it.votoPromedio.toDoubleOrNull() ?: 0.0,
                                    it.tipoPelicula,
                                    it.generos
                                    )
                        }
                        true
                    }
                    R.id.compartir -> {
                        pelicula?.let {
                            val movieId = it.id.toString()
                            val typemovie = it.tipoPelicula
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Te recomiendo esta película de filmovil: https://filmovil.com/?id=$movieId&type=$typemovie")
                                type = "text/plain"
                                setPackage("com.whatsapp")
                            }

                            try {
                                startActivity(sendIntent)
                            } catch (ex: ActivityNotFoundException) {
                                Toast.makeText(this, "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        true

                    }

                    else -> false
                }
            }
            desplegable.show()
        }
    }
}