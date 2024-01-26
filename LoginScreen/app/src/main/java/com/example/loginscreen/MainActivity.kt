package com.example.loginscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginscreen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener() {
            val intentSignUpActivity = Intent(this, SignUpActivity::class.java)
            startActivity(intentSignUpActivity)
        }

        binding.btLogin.setOnClickListener() {
            val email: String = binding.etEmailSignUp.text.toString()
            val passWord: String = binding.etPasswordSignUp.text.toString()
            val listUser = intent.getSerializableExtra("listUser") as ArrayList<User>?
            var ok = false
            if (listUser != null) {
                for (users in listUser) {
                    if (email == users.email && passWord == users.passWord) {
                        ok = true
                        val intentLoggedInActivity = Intent(this, LoggedInActivity::class.java)
                        startActivity(intentLoggedInActivity)
                    }
                }
                if (!ok) {
                    Toast.makeText(
                        applicationContext,
                        "Thong tin dang nhap khong chinh xac",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}