package com.nathan.medlock.model

data class Doctor (
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val specialty: String,
    val yearsOfExperience: Int
)