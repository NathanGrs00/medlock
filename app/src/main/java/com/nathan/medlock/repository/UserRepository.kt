package com.nathan.medlock.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nathan.medlock.model.User

class UserRepository {
    fun saveUser(user: User, callback: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(user.id)
            .set(user)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { _: Exception ->
                callback(false)
            }
    }

    fun getUser(userId: String, callback: (User?) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    callback(user)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { _: Exception ->
                callback(null)
            }
    }

    fun getUserByEmail(email: String, callback: (User?) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                val user = documents.firstOrNull()?.toObject(User::class.java)
                callback(user)
            }
            .addOnFailureListener { callback(null) }
    }
}