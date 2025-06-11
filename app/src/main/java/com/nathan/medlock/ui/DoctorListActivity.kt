package com.nathan.medlock.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nathan.medlock.R
import com.nathan.medlock.model.Doctor
import com.nathan.medlock.ui.adapters.DoctorAdapter

class DoctorListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_doctors)

        val doctors = listOf(
            Doctor("1", "Dr. Alice Smith", 40, "F","Cardiology", 15),
            Doctor("2","Dr. Bob Jones", 50, "M","Neurology", 22),
            Doctor("3","Dr. Carol Lee", 35, "F","Pediatrics", 10)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerDoctors)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DoctorAdapter(doctors)
    }
}