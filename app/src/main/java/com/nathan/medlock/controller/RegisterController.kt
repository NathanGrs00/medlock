package com.nathan.medlock.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.nathan.medlock.model.User
import com.nathan.medlock.repository.UserRepository

class RegisterController {
    private lateinit var auth: FirebaseAuth

    fun checkRegistration(
        email: String,
        password: String,
        username: String,
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        auth = FirebaseAuth.getInstance()

        when {
            email.isEmpty() || password.isEmpty() -> {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                callback(false)
            }
            password.length < 8 -> {
                Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                callback(false)
            }
            !isValidPassword(password) -> {
                Toast.makeText(context, "Password must contain a letter, a number, and a special character", Toast.LENGTH_SHORT).show()
                callback(false)
            }
            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        Log.d("UserDetailActivity", "userId : ${auth.currentUser?.uid}")
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: ""
                            val user = User(
                                id = userId,
                                username = username,
                                timeCreated = Timestamp.now(),
                                timeBlocked = Timestamp.now(),
                                isBlocked = false,
                                timeLastLogin = Timestamp.now()
                            )
                            val userRepository = UserRepository()
                            userRepository.saveUser(user) { success ->
                                if (success) {
                                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                                    callback(true)
                                } else {
                                    Toast.makeText(context, "Failed to save user data", Toast.LENGTH_SHORT).show()
                                    callback(false)
                                }
                            }
                        } else {
                            Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            callback(false)
                        }
                    }
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val hasLetter = password.contains(Regex("[A-Za-z]"))
        val hasDigit = password.contains(Regex("\\d"))
        val hasSpecial = password.contains(Regex("[^A-Za-z\\d]"))
        return hasLetter && hasDigit && hasSpecial
    }
}