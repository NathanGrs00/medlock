package com.nathan.medlock.controller

import com.google.firebase.auth.FirebaseAuth
import com.nathan.medlock.repository.UserRepository

class LoginController(private val userRepository: UserRepository) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val loginAttempts = mutableMapOf<String, Int>()

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val uid = authResult.user?.uid ?: ""
                userRepository.getUser(uid) { user ->
                    if (user == null) {
                        onResult(false, "User not found")
                        return@getUser
                    }
                    if (user.isBlocked) {
                        onResult(false, "Account is blocked")
                        return@getUser
                    }
                    loginAttempts[email] = 0
                    onResult(true, null)
                }
            }
            .addOnFailureListener {
                val attempts = loginAttempts.getOrDefault(email, 0) + 1
                loginAttempts[email] = attempts
                if (attempts >= 3) {
                    // Optionally block user here
                    onResult(false, "Account is blocked after 3 failed attempts")
                } else {
                    onResult(false, "Invalid credentials")
                }
            }
    }
}