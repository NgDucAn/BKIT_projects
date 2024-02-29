package com.example.sharedpreferencesloginscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.sharedpreferencesloginscreen.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isChecked: Boolean = false

    private val logoutReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.etEmailSignUp.text.clear()
            binding.etPasswordSignUp.text.clear()
            binding.cbRemember. isChecked = false
            isChecked = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        // Lấy danh sách người dùng từ SharedPreferences
        val userListJson = sharedPreferences.getString("USER_LIST", null)
        val type = object : TypeToken<ArrayList<User>>() {}.type
        val userList: ArrayList<User> = Gson().fromJson(userListJson, type) ?: ArrayList()

        val filter = IntentFilter("LOGOUT")
        registerReceiver(logoutReceiver, filter)

        binding.btLogin.setOnClickListener() {
            if (TextUtils.isEmpty(binding.etEmailSignUp.text.toString()) && TextUtils.isEmpty(
                    binding.etPasswordSignUp.text.toString()
                )
            ) {
                Toast.makeText(this, "Hãy nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                val email = binding.etEmailSignUp.text.toString()
                val password = binding.etPasswordSignUp.text.toString()
                var isUserFound = false

                for (user in userList) {
                    if (user.email == email && user.passWord == password) {
                        isUserFound = true
                        break
                    }
                }

                if (isUserFound) {
                    if (binding.cbRemember.isChecked) {
                        isChecked = true
                        sharedPreferences.edit().putBoolean("rememberME", isChecked).apply()
                    }

                    val intentLoggedInActivity = Intent(this, LoggedInActivity::class.java)
                    startActivity(intentLoggedInActivity)
                } else {
                    Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            val intentSignUpActivity = Intent(this, SignUpActivity::class.java)
            startActivity(intentSignUpActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        val isCheckedFromPrefs = sharedPreferences.getBoolean("rememberME", false)

        if (isCheckedFromPrefs) {
            Log.d("RememberMe", "onStart: isChecked is $isCheckedFromPrefs")

            val intentLoggedInActivity = Intent(this, LoggedInActivity::class.java)
            startActivity(intentLoggedInActivity)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(logoutReceiver)
    }
}