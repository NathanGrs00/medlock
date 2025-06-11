package com.nathan.medlock.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nathan.medlock.model.Doctor

class DoctorRepository {
    fun getAllDoctors(callback: (List<Doctor>) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("doctors")
            .get()
            .addOnSuccessListener { result ->
                val doctors = result.mapNotNull { doc ->
                    try {
                        Doctor(
                            id = doc.id,
                            name = doc.getString("name") ?: "",
                            age = doc.getLong("age")?.toInt() ?: 0,
                            gender = doc.getString("gender") ?: "",
                            specialty = doc.getString("specialty") ?: "",
                            yearsOfExperience = doc.getLong("yearsOfExperience")?.toInt() ?: 0
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                callback(doctors)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }
}
