package com.nathan.medlock.ui

import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import android.widget.Toast

class ChangePasswordActivity {
    fun sendPasswordReset(context: Context, email: String) {
        if (email.isEmpty()) {
            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Password reset email sent. Check your inbox.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to send reset email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

