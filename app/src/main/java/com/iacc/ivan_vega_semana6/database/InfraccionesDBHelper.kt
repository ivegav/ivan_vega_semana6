package com.iacc.ivan_vega_semana6.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.iacc.ivan_vega_semana6.entidades.Infraccion
import kotlin.math.log

class InfraccionesDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "infracciones.db"
        private const val TABLE_NAME = "infracciones"
        private const val COLUMN_FOLIO = "folio"
        private const val COLUMN_RUT_INSPECTOR = "rut_inspector"
        private const val COLUMN_NOMBRE_LOCAL = "nombre_local"
        private const val COLUMN_DIRECCION = "direccion"
        private const val COLUMN_INFRACCION = "infraccion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_FOLIO INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_RUT_INSPECTOR TEXT,"
                + "$COLUMN_NOMBRE_LOCAL TEXT,"
                + "$COLUMN_DIRECCION TEXT,"
                + "$COLUMN_INFRACCION TEXT)")
        db.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun agregarInfraccion(infraccion: Infraccion): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RUT_INSPECTOR, infraccion.rutInspector)
            put(COLUMN_NOMBRE_LOCAL, infraccion.nombreLocal)
            put(COLUMN_DIRECCION, infraccion.direccion)
            put(COLUMN_INFRACCION, infraccion.tipoInfraccion)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()

        Log.v("InfraccionesDBHelper", "Infracción agregada con folio $id")
        return id
    }

    fun getAllInfracciones(): ArrayList<Infraccion> {
        val infracciones = ArrayList<Infraccion>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor? = db.rawQuery(query, null)
        cursor?.use {
            val rutIndex = it.getColumnIndex(COLUMN_RUT_INSPECTOR)
            val nombreIndex = it.getColumnIndex(COLUMN_NOMBRE_LOCAL)
            val direccionIndex = it.getColumnIndex(COLUMN_DIRECCION)
            val infraccionIndex = it.getColumnIndex(COLUMN_INFRACCION)
            val folioIndex = it.getColumnIndex(COLUMN_FOLIO)

            while (it.moveToNext()) {
                val rutInspector = if (rutIndex != -1) it.getString(rutIndex) else ""
                val nombreLocal = if (nombreIndex != -1) it.getString(nombreIndex) else ""
                val direccion = if (direccionIndex != -1) it.getString(direccionIndex) else ""
                val tipoInfraccion = if (infraccionIndex != -1) it.getString(infraccionIndex) else ""
                val folio = if (folioIndex != -1) it.getInt(folioIndex).toString() else ""

                val infraccion = Infraccion(rutInspector, nombreLocal, direccion, tipoInfraccion)
                infraccion.folio = folio
                Log.v("InfraccionesDBHelper", "Infracción encontrada con folio ${infraccion.folio}")
                infracciones.add(infraccion)
            }
        }
        cursor?.close()
        db.close()
        return infracciones
    }

    fun actualizarInfraccion(infraccion: Infraccion): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RUT_INSPECTOR, infraccion.rutInspector)
            put(COLUMN_NOMBRE_LOCAL, infraccion.nombreLocal)
            put(COLUMN_DIRECCION, infraccion.direccion)
            put(COLUMN_INFRACCION, infraccion.tipoInfraccion)
        }

        val whereClause = "$COLUMN_FOLIO = ?"
        val whereArgs = arrayOf(infraccion.folio)


        Log.v("InfraccionesDBHelper", "Actualizando infracción con folio ${infraccion.folio}")
        val resultado = db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
        return resultado != -1
    }
}