package com.nathan.medlock.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nathan.medlock.R
import com.nathan.medlock.controller.DoctorController
import com.nathan.medlock.ui.adapters.DoctorAdapter

class DoctorListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_doctors)
        val doctorController = DoctorController()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerDoctors)
        recyclerView.layoutManager = LinearLayoutManager(this)

        doctorController.getAllDoctors { doctors ->
            recyclerView.adapter = DoctorAdapter(doctors)
        }
    }
}