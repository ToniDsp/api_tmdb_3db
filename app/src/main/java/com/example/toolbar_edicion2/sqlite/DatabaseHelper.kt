package com.example.toolbar_edicion2.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.toolbar_edicion2.infoPeliculas.InfoPeliculas

class DatabaseHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "peliculas_favoritas"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Creamos tabla
        if (db != null) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS peliculas_favoritas (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "idPelicula TEXT," +
                        "nombrePelicula TEXT," +
                        "titulo TEXT," +
                        "descripcion TEXT," +
                        "poster TEXT," +
                        "fechaLanzamiento TEXT," +
                        "votoPromedio REAL," +
                        "tipoPelicula TEXT," +
                        "estado BOOLEAN DEFAULT 0);"
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Actualizar la base de datos
        db?.execSQL("DROP TABLE IF EXISTS peliculas_favoritas;")
        onCreate(db)
    }

    fun insertData(
        idPelicula: String?,
        nombrePelicula: String?,
        titulo: String?,
        descripcion: String?,
        poster: String?,
        fechaLanzamiento: String?,
        votoPromedio: Double?,
        tipoPelicula: String?
    ): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idPelicula", idPelicula ?: "Sin id")
            put("nombrePelicula", nombrePelicula ?: "Sin nombre")
            put("titulo", titulo ?: "Sin título")
            put("descripcion", descripcion ?: "Sin descripción")
            put("poster", poster ?: "Sin poster")
            put("fechaLanzamiento", fechaLanzamiento ?: "Sin fecha de lanzamiento")
            put("votoPromedio", votoPromedio ?: 0.0)
            put("tipoPelicula", tipoPelicula ?: "Sin tipo")
            put("estado", 0)
        }

        val cursor = db.rawQuery("SELECT * FROM peliculas_favoritas WHERE idPelicula = ?", arrayOf(idPelicula))
        val exists = cursor.moveToFirst()
        cursor.close()

        val newRowId = if (!exists) {
            db.insert("peliculas_favoritas", null, values)
        } else {
            Toast.makeText(context, "Error. El contenido ya existe", Toast.LENGTH_LONG).show()
            -1
        }
        db.close()
        return newRowId
    }

    fun checkIfIdExists(idPelicula: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM peliculas_favoritas WHERE idPelicula = ?", arrayOf(idPelicula))
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    fun SelectSinEstado(): List<InfoPeliculas> {
        val listaPelicula = mutableListOf<InfoPeliculas>()
        val db = readableDatabase
        try {
            val cursor = db.rawQuery(
                "SELECT idPelicula, nombrePelicula, titulo, descripcion, poster, fechaLanzamiento, votoPromedio, tipoPelicula FROM peliculas_favoritas",
                null
            )
            if (cursor.moveToFirst()) {
                do {
                    val idPeliculaIndex = cursor.getColumnIndex("idPelicula")
                    val nombrePeliculaIndex = cursor.getColumnIndex("nombrePelicula")
                    val tituloIndex = cursor.getColumnIndex("titulo")
                    val descripcionIndex = cursor.getColumnIndex("descripcion")
                    val posterIndex = cursor.getColumnIndex("poster")
                    val fechaLanzamientoIndex = cursor.getColumnIndex("fechaLanzamiento")
                    val votoPromedioIndex = cursor.getColumnIndex("votoPromedio")
                    val tipoPeliculaIndex = cursor.getColumnIndex("tipoPelicula")

                    if (idPeliculaIndex != -1 && nombrePeliculaIndex != -1 && tituloIndex != -1 && descripcionIndex != -1 && posterIndex != -1 && fechaLanzamientoIndex != -1 && votoPromedioIndex != -1 && tipoPeliculaIndex != -1) {
                        val pelicula = InfoPeliculas(
                            cursor.getString(idPeliculaIndex),
                            cursor.getString(nombrePeliculaIndex),
                            cursor.getString(tituloIndex),
                            cursor.getString(descripcionIndex),
                            cursor.getString(posterIndex),
                            cursor.getString(fechaLanzamientoIndex),
                            cursor.getString(votoPromedioIndex),
                            cursor.getString(tipoPeliculaIndex)
                        )

                        listaPelicula.add(pelicula)
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return listaPelicula
    }
    @SuppressLint("Range")
    fun checkVisto(idPelicula: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT estado FROM peliculas_favoritas WHERE idPelicula = ?", arrayOf(idPelicula))
        var estado = false
        if (cursor.moveToFirst()) {
            estado = cursor.getInt(cursor.getColumnIndex("estado")) == 1
        }
        cursor.close()
        db.close()
        return estado
    }
    fun marcarComoVisto(idPelicula: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("estado", 1)
        }
        db.update("peliculas_favoritas", values, "idPelicula = ?", arrayOf(idPelicula))
        db.close()
    }
    fun borrarFavorito(idPelicula: String) {
        val db = writableDatabase
        db.delete("peliculas_favoritas", "idPelicula = ?", arrayOf(idPelicula))
        db.close()
    }
}