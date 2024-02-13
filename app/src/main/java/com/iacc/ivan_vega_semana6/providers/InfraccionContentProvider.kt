package com.iacc.ivan_vega_semana6.providers

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri
import com.iacc.ivan_vega_semana6.database.InfraccionesDBHelper

class InfraccionContentProvider : ContentProvider() {

    private lateinit var dbHelper: InfraccionesDBHelper

    companion object {
        const val AUTHORITY = "com.iacc.ivan_vega_semana6.providers"
        const val PATH_INFRACCIONES = "infracciones"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH_INFRACCIONES")

        private const val INFRACCIONES = 1
        private const val INFRACCION_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, PATH_INFRACCIONES, INFRACCIONES)
            uriMatcher.addURI(AUTHORITY, "$PATH_INFRACCIONES/#", INFRACCION_ID)
        }
    }

    override fun onCreate(): Boolean {
        dbHelper = InfraccionesDBHelper(context as Context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            INFRACCIONES -> cursor = db.query(
                InfraccionesDBHelper.TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder
            )
            INFRACCION_ID -> {
                val id = uri.lastPathSegment
                val whereClause = "${InfraccionesDBHelper.COLUMN_FOLIO} = $id"
                cursor = db.query(
                    InfraccionesDBHelper.TABLE_NAME, projection, whereClause,
                    selectionArgs, null, null, sortOrder
                )
            }
            else -> throw IllegalArgumentException("URI desconocida: $uri")
        }
        cursor?.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val id = db.insert(InfraccionesDBHelper.TABLE_NAME, null, values)
        if (id > 0) {
            val newUri = ContentUris.withAppendedId(CONTENT_URI, id)
            context?.contentResolver?.notifyChange(newUri, null)
            return newUri
        }
        throw SQLException("Error al insertar el registro en $uri")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = dbHelper.writableDatabase
        val rowsUpdated = when (uriMatcher.match(uri)) {
            INFRACCIONES -> db.update(InfraccionesDBHelper.TABLE_NAME, values, selection, selectionArgs)
            INFRACCION_ID -> {
                val id = uri.lastPathSegment
                val whereClause = "${InfraccionesDBHelper.COLUMN_FOLIO} = $id"
                db.update(InfraccionesDBHelper.TABLE_NAME, values, whereClause, selectionArgs)
            }
            else -> throw IllegalArgumentException("URI desconocida: $uri")
        }
        if (rowsUpdated != 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rowsUpdated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val rowsDeleted = when (uriMatcher.match(uri)) {
            INFRACCIONES -> db.delete(InfraccionesDBHelper.TABLE_NAME, selection, selectionArgs)
            INFRACCION_ID -> {
                val id = uri.lastPathSegment
                val whereClause = "${InfraccionesDBHelper.COLUMN_FOLIO} = $id"
                db.delete(InfraccionesDBHelper.TABLE_NAME, whereClause, selectionArgs)
            }
            else -> throw IllegalArgumentException("URI desconocida: $uri")
        }
        if (rowsDeleted != 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rowsDeleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}