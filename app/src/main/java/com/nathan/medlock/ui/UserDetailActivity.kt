package com.nathan.medlock.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nathan.medlock.R
import com.nathan.medlock.repository.UserRepository

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        // Always set this so you can see if the activity is loaded
        findViewById<TextView>(R.id.textId).text = "Bananabread"

        val userId = FirebaseAuth.getInstance().uid
        Log.d("UserDetailActivity", "userId: $userId")

        if (userId == null) {
            findViewById<TextView>(R.id.textUsername).text = "Not logged in"
            return
        }

        UserRepository().getUser(userId) { user ->
            user?.let {
                findViewById<TextView>(R.id.textId).text = "ID: ${it.id}"
                findViewById<TextView>(R.id.textUsername).text = "Username: ${it.username}"
                findViewById<TextView>(R.id.textTimeCreated).text =
                    "Created: ${it.timeCreated.toDate()}"
                findViewById<TextView>(R.id.textTimeBlocked).text =
                    "Blocked: ${it.timeBlocked.toDate()}"
                findViewById<TextView>(R.id.textIsBlocked).text = "Is Blocked: ${it.isBlocked}"
                findViewById<TextView>(R.id.textTimeLastLogin).text =
                    "Last Login: ${it.timeLastLogin.toDate()}"
            }
        }
    }
}