package com.nathan.medlock.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nathan.medlock.R
import com.nathan.medlock.model.Doctor

class DoctorAdapter(private val doctors: List<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textName)
        val specialty: TextView = itemView.findViewById(R.id.textSpecialty)
        val experience: TextView = itemView.findViewById(R.id.textExperience)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.name.text = doctor.name
        holder.specialty.text = "Specialty: ${doctor.specialty}"
        holder.experience.text = "Experience: ${doctor.yearsOfExperience} years"
    }

    override fun getItemCount() = doctors.size
}