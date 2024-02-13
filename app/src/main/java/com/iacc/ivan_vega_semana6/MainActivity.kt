package com.iacc.ivan_vega_semana6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonRegistrarInfraccion: Button = findViewById(R.id.buttonRegistrarInfraccion)
        val buttonVisualizarInfracciones: Button = findViewById(R.id.buttonVisualizarInfracciones)

        buttonRegistrarInfraccion.setOnClickListener {
            val intent = Intent(this, RegistrarInfraccionActivity::class.java)
            startActivity(intent)
        }

        buttonVisualizarInfracciones.setOnClickListener {
            val intent = Intent(this, VisualizarInfraccionesActivity::class.java)
            startActivity(intent)
        }
    }
}