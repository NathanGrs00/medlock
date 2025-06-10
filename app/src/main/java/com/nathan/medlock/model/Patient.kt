package com.nathan.medlock.model

import com.google.firebase.Timestamp

data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val registrationDate: Timestamp
)