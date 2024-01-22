package com.example.sharedpreferencesloginscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedpreferencesloginscreen.databinding.ActivityLoggedInBinding

class LoggedInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoggedInBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)

        binding.btLogOut.setOnClickListener() {
            val intentLogout = Intent("LOGOUT")
            sendBroadcast(intentLogout)
            sharedPreferences.edit().putBoolean("rememberME", false).apply()
            val intentMainActivity = Intent(this, MainActivity::class.java)
            startActivity(intentMainActivity)
        }
    }
}