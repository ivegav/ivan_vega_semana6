package com.iacc.ivan_vega_semana6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iacc.ivan_vega_semana6.database.InfraccionesDBHelper
import com.iacc.ivan_vega_semana6.entidades.Infraccion

class VisualizarInfraccionesActivity : AppCompatActivity(), InfraccionesAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InfraccionesAdapter
    private lateinit var dbHelper: InfraccionesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_infracciones)

        dbHelper = InfraccionesDBHelper(this)

        recyclerView = findViewById(R.id.recyclerViewInfracciones)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar las infracciones desde la base de datos
        val listaInfracciones = dbHelper.getAllInfracciones()

        adapter = InfraccionesAdapter(listaInfracciones)
        recyclerView.adapter = adapter

        // Establecer el listener de clics en el adaptador
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(infraccion: Infraccion) {
        // Navegar a la actividad de detalle de infracción con los datos de la infracción seleccionada
        val intent = Intent(this, DetalleInfraccionActivity::class.java)
        intent.putExtra("rutInspector", infraccion.rutInspector)
        intent.putExtra("nombreLocal", infraccion.nombreLocal)
        intent.putExtra("direccion", infraccion.direccion)
        intent.putExtra("tipoInfraccion", infraccion.tipoInfraccion)
        intent.putExtra("folio", infraccion.folio)
        startActivity(intent)
    }
}