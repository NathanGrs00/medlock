package com.nathan.medlock.ui

import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nathan.medlock.R
import com.nathan.medlock.controller.LoginController

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var failCounter = 0

        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val registerButton = findViewById<TextView>(R.id.textViewNew)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            val loginController = LoginController()
            loginController.checkLogin(email, password, this) { success, isBlocked ->
                if (isBlocked) {
                    loginButton.isEnabled = false
                    Toast.makeText(this, "Account is blocked. Please contact support.", Toast.LENGTH_LONG).show()
                    return@checkLogin
                }
                if (!success) {
                    failCounter++
                    if (failCounter >= 3) {
                        val loginController = LoginController()
                        loginController.blockUserByEmail(email) { blocked ->
                            if (blocked) {
                                Toast.makeText(this, "Too many failed attempts. Account is now blocked.", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this, "Failed to block account. Try again.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Login failed. Attempt $failCounter of 3.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val biometricManager = BiometricManager.from(this)
                    if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                        BiometricManager.BIOMETRIC_SUCCESS) {

                        val executor = ContextCompat.getMainExecutor(this)
                        val biometricPrompt = BiometricPrompt(this, executor,
                            object : BiometricPrompt.AuthenticationCallback() {
                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                    super.onAuthenticationSucceeded(result)
                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                    super.onAuthenticationError(errorCode, errString)
                                    // Only show a message, do not navigate
                                    Toast.makeText(this@LoginActivity, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                                }

                                override fun onAuthenticationFailed() {
                                    super.onAuthenticationFailed()
                                    Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                                }
                            })

                        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Login with Fingerprint")
                            .setSubtitle("Use your fingerprint to login")
                            .setNegativeButtonText("Cancel")
                            .build()

                        biometricPrompt.authenticate(promptInfo)
                    } else {
                        Toast.makeText(this, "Fingerprint not available", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}