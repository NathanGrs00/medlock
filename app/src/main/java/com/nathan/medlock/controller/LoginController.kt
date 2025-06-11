package com.nathan.medlock.controller

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginController {
    private lateinit var auth: FirebaseAuth

    fun checkLogin(email: String, password: String, context: Context, callback: (Boolean) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            callback(false)
            return
        }

        auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        callback(true)
                    } else {
                        Toast.makeText(context, "Please verify your email before logging in.", Toast.LENGTH_LONG).show()
                        callback(false)
                    }
                } else {
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }
    }
}