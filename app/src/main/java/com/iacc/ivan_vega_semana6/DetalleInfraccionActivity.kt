package com.iacc.ivan_vega_semana6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iacc.ivan_vega_semana6.database.InfraccionesDBHelper
import com.iacc.ivan_vega_semana6.entidades.Infraccion

class DetalleInfraccionActivity : AppCompatActivity() {

    private lateinit var dbHelper: InfraccionesDBHelper
    private var folio: String = ""

    lateinit var editTextRutInspector: TextView
    lateinit var editTextNombreLocal: TextView
    lateinit var editTextDireccion: TextView
    lateinit var editTextTipoInfraccion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_infraccion)

        editTextRutInspector = findViewById(R.id.editTextRutInspector)
        editTextNombreLocal = findViewById(R.id.editTextNombreLocal)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextTipoInfraccion = findViewById(R.id.editTextTipoInfraccion)


        dbHelper = InfraccionesDBHelper(this)

        folio = intent.getStringExtra("folio") ?: ""
        val rutInspector = intent.getStringExtra("rutInspector") ?: ""
        val nombreLocal = intent.getStringExtra("nombreLocal") ?: ""
        val direccion = intent.getStringExtra("direccion") ?: ""
        val tipoInfraccion = intent.getStringExtra("tipoInfraccion") ?: ""

        findViewById<TextView>(R.id.textViewFolio).text = folio
        editTextRutInspector.text = rutInspector
        editTextNombreLocal.text = nombreLocal
        editTextDireccion.text = direccion
        editTextTipoInfraccion.text = tipoInfraccion

        val buttonGuardar: Button = findViewById(R.id.buttonGuardar)
        val buttonCompartir: Button = findViewById(R.id.buttonCompartir)

        buttonGuardar.setOnClickListener {
            guardarCambios()
        }

        buttonCompartir.setOnClickListener {
            compartirInformacion()
        }
    }

    private fun guardarCambios() {
        val rutInspector = editTextRutInspector.text.toString()
        val nombreLocal = editTextNombreLocal.text.toString()
        val direccion = editTextDireccion.text.toString()
        val tipoInfraccion = editTextTipoInfraccion.text.toString()

        // Actualizar la infracción en la base de datos
        val infraccion = Infraccion(rutInspector, nombreLocal, direccion, tipoInfraccion)
        infraccion.folio = folio
        val resultado = dbHelper.actualizarInfraccion(infraccion)

        // Manejar el resultado
        if (resultado) {
            // Guardado exitoso
            Toast.makeText(this, "Cambios guardados correctamente", Toast.LENGTH_SHORT).show()
        } else {
            // Error al guardar
            Toast.makeText(this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show()
        }
    }


    private fun compartirInformacion() {
        // Se obtienen los datos de la infracción desde los campos de texto
        val rutInspector = editTextRutInspector.text.toString()
        val nombreLocal = editTextNombreLocal.text.toString()
        val direccion = editTextDireccion.text.toString()
        val tipoInfraccion = editTextTipoInfraccion.text.toString()

        // Se crea un mensaje con los datos de la infracción
        val mensaje = "Detalles de la infracción:\n" +
                "RUT del Inspector: $rutInspector\n" +
                "Nombre del Local: $nombreLocal\n" +
                "Dirección: $direccion\n" +
                "Infracción: $tipoInfraccion"

        // Se crea un Intent para compartir el mensaje
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, mensaje)
            type = "text/plain"
        }

        // Se inicia una actividad para mostrar las aplicaciones disponibles para compartir
        startActivity(Intent.createChooser(intent, "Compartir información"))
    }
}