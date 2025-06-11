package com.nathan.medlock.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.nathan.medlock.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val buttonProfileDetails = findViewById<Button>(R.id.buttonUserDetails)
        buttonProfileDetails.setOnClickListener {
            startActivity(Intent(this, UserDetailActivity::class.java))
        }

        val buttonDoctorList = findViewById<Button>(R.id.buttonDoctorsList)
        buttonDoctorList.setOnClickListener {
            startActivity(Intent(this, DoctorListActivity::class.java))
        }

        val buttonLogout = findViewById<Button>(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}