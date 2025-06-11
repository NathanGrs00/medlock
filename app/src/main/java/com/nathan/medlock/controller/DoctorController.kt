package com.nathan.medlock.controller

import com.nathan.medlock.model.Doctor
import com.nathan.medlock.repository.DoctorRepository

class DoctorController {
    private val doctorRepository = DoctorRepository()

    fun getAllDoctors(callback: (List<Doctor>) -> Unit) {
        doctorRepository.getAllDoctors { doctors ->
            callback(doctors)
        }
    }
}