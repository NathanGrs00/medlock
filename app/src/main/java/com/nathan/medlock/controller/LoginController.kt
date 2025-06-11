package com.nathan.medlock.controller

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginController {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun checkLogin(email: String, password: String, context: Context, callback: (Boolean, Boolean) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            callback(false, false)
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        // Query Firestore for user document by UID
                        db.collection("users").document(user.uid).get()
                            .addOnSuccessListener { userDoc ->
                                val isBlocked = userDoc.getBoolean("isBlocked") ?: false
                                if (isBlocked) {
                                    Toast.makeText(context, "Account is blocked. Contact support.", Toast.LENGTH_LONG).show()
                                    callback(false, true)
                                } else {
                                    callback(true, false)
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to fetch user data.", Toast.LENGTH_SHORT).show()
                                callback(false, false)
                            }
                    } else {
                        Toast.makeText(context, "Please verify your email before logging in.", Toast.LENGTH_LONG).show()
                        callback(false, false)
                    }
                } else {
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    callback(false, false)
                }
            }
    }

    fun blockUserByEmail(email: String, callback: (Boolean) -> Unit) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val doc = querySnapshot.documents[0]
                    doc.reference.update("isBlocked", true)
                        .addOnSuccessListener { callback(true) }
                        .addOnFailureListener { callback(false) }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { callback(false) }
    }
}