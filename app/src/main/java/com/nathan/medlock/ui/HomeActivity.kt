package com.nathan.medlock.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.nathan.medlock.R

class HomeActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Write a value
        val testData = hashMapOf("message" to "Hello, Firestore!")
        db.collection("testCollection").document("testDoc")
            .set(testData)
            .addOnSuccessListener {
                Log.d("FirestoreTest", "Write successful")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreTest", "Write failed", e)
            }

        // Read the value
        db.collection("testCollection").document("testDoc")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    Log.d("FirestoreTest", "Value: ${document.getString("message")}")
                } else {
                    Log.d("FirestoreTest", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreTest", "Read failed", e)
            }
    }
}