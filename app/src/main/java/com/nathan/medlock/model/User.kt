package com.nathan.medlock.model

import com.google.firebase.Timestamp

data class User (
    val id: String = "",
    val username: String = "",
    val timeCreated: Timestamp = Timestamp.now(),
    val timeBlocked: Timestamp = Timestamp.now(),
    val isBlocked: Boolean = false,
    val timeLastLogin: Timestamp = Timestamp.now()
)