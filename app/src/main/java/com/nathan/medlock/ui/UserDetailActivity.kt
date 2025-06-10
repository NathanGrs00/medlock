package com.nathan.medlock.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nathan.medlock.R
import com.nathan.medlock.model.User
import com.google.firebase.Timestamp

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        // Example User, replace with real data as needed
        val user = intent.getSerializableExtra("user") as? User ?: User(
            id = "123",
            username = "nathangrs00",
            timeCreated = Timestamp.now(),
            timeBlocked = Timestamp.now(),
            isBlocked = false,
            timeLastLogin = Timestamp.now()
        )

        findViewById<TextView>(R.id.textId).text = "ID: ${user.id}"
        findViewById<TextView>(R.id.textUsername).text = "Username: ${user.username}"
        findViewById<TextView>(R.id.textTimeCreated).text = "Created: ${user.timeCreated.toDate()}"
        findViewById<TextView>(R.id.textTimeBlocked).text = "Blocked: ${user.timeBlocked.toDate()}"
        findViewById<TextView>(R.id.textIsBlocked).text = "Is Blocked: ${user.isBlocked}"
        findViewById<TextView>(R.id.textTimeLastLogin).text = "Last Login: ${user.timeLastLogin.toDate()}"
    }
}