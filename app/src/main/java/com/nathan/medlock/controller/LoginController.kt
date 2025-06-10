package com.nathan.medlock.controller

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nathan.medlock.ui.HomeActivity

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
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                    callback(true)
                } else {
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }
    }
}