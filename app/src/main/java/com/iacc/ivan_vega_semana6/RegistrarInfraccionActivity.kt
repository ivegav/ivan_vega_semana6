package com.iacc.ivan_vega_semana6

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.iacc.ivan_vega_semana6.database.InfraccionesDBHelper
import com.iacc.ivan_vega_semana6.entidades.Infraccion

class RegistrarInfraccionActivity : AppCompatActivity() {

    private lateinit var rutInspectorEditText: EditText
    private lateinit var nombreLocalEditText: EditText
    private lateinit var direccionEditText: EditText
    private lateinit var infraccionEditText: EditText
    private lateinit var dbHelper: InfraccionesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_infraccion)

        dbHelper = InfraccionesDBHelper(this)

        rutInspectorEditText = findViewById(R.id.editTextRutInspector)
        nombreLocalEditText = findViewById(R.id.editTextNombreLocal)
        direccionEditText = findViewById(R.id.editTextDireccion)
        infraccionEditText = findViewById(R.id.editTextInfraccion)

        val buttonRegistrarInfraccion: Button = findViewById(R.id.buttonRegistrarInfraccion)

        buttonRegistrarInfraccion.setOnClickListener {
            registrarInfraccion()
        }
    }

    private fun registrarInfraccion() {
        val rutInspector = rutInspectorEditText.text.toString()
        val nombreLocal = nombreLocalEditText.text.toString()
        val direccion = direccionEditText.text.toString()
        val infraccion = infraccionEditText.text.toString()

        if (rutInspector.isNotEmpty() && nombreLocal.isNotEmpty() && direccion.isNotEmpty() && infraccion.isNotEmpty()) {
            val nuevaInfraccion = Infraccion(rutInspector, nombreLocal, direccion, infraccion)
            val id = dbHelper.agregarInfraccion(nuevaInfraccion)

            val folio = id.toString()

            mostrarAlertaFolioGenerado(folio)

            limpiarCampos()

        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarAlertaFolioGenerado(folio: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Infracción registrada correctamente")
        alertDialogBuilder.setMessage("Se ha generado el folio N° $folio asociado a la infracción.")
        alertDialogBuilder.setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun limpiarCampos() {
        rutInspectorEditText.text.clear()
        nombreLocalEditText.text.clear()
        direccionEditText.text.clear()
        infraccionEditText.text.clear()
    }
}