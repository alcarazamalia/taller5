package com.example.myapp2023.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2023.R
import com.example.myapp2023.model.CitasMedicas
import com.example.myapp2023.util.PreferenceHelper
import java.util.ArrayList

class AdapterMisCitas(private val context: Context, private val misCitas: List<CitasMedicas>)
    : RecyclerView.Adapter<AdapterMisCitas.ViewHolder>() {

    private val citasGuardadas: ArrayList<CitasMedicas> = ArrayList()

    init {
        citasGuardadas.addAll(PreferenceHelper.getCitas(context))
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvMisCitasId = itemView.findViewById<TextView>(R.id.tv_id)
        val tvDoctorName = itemView.findViewById<TextView>(R.id.tv_medico)
        val tvScheduledDate = itemView.findViewById<TextView>(R.id.tv_fecha)
        val tvScheduledHora = itemView.findViewById<TextView>(R.id.tv_hora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mis_citas, parent, false)
        )
    }

    override fun getItemCount() = citasGuardadas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cita = citasGuardadas[position]
        holder.tvMisCitasId.text = "Cita médica N#${cita.id}"
        holder.tvDoctorName.text = cita.doctorName
        holder.tvScheduledDate.text = "Atención el dia ${cita.scheduledDate}"
        holder.tvScheduledHora.text = "A las ${cita.scheduledTime}"
    }
}