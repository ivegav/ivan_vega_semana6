package com.iacc.ivan_vega_semana6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iacc.ivan_vega_semana6.entidades.Infraccion

class InfraccionesAdapter(private val listaInfracciones: List<Infraccion>) :
    RecyclerView.Adapter<InfraccionesAdapter.InfraccionViewHolder>() {

    // Interfaz para manejar los clics en los elementos de la lista
    interface OnItemClickListener {
        fun onItemClick(infraccion: Infraccion)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class InfraccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Aquí puedes definir los elementos de la vista de cada item de la lista, por ejemplo:
        val textViewRutInspector: TextView = itemView.findViewById(R.id.textViewRutInspector)
        val textViewNombreLocal: TextView = itemView.findViewById(R.id.textViewNombreLocal)
        val textViewDireccion: TextView = itemView.findViewById(R.id.textViewDireccion)
        val textViewInfraccion: TextView = itemView.findViewById(R.id.textViewInfraccion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfraccionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_infraccion, parent, false)
        return InfraccionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfraccionViewHolder, position: Int) {
        val infraccion = listaInfracciones[position]
        // Aquí puedes establecer los datos de la infracción en los elementos de la vista
        holder.textViewRutInspector.text = infraccion.rutInspector
        holder.textViewNombreLocal.text = infraccion.nombreLocal
        holder.textViewDireccion.text = infraccion.direccion
        holder.textViewInfraccion.text = infraccion.tipoInfraccion

        holder.itemView.setOnClickListener {
            listener?.onItemClick(infraccion) // Llamar al listener cuando se hace clic en un elemento
        }
    }

    override fun getItemCount(): Int {
        return listaInfracciones.size
    }

}