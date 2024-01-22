package com.example.sharedpreferencesloginscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.sharedpreferencesloginscreen.databinding.ActivitySignUpBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
    }

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        binding.btSignUp.setOnClickListener() {
            if (TextUtils.isEmpty(binding.etEmailSignUp.text.toString()) && TextUtils.isEmpty(
                    binding.etPasswordSignUp.text.toString()
                )
            ) {
                Toast.makeText(this, "Hãy nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                // Tạo người dùng mới
                val newUser = User(
                    binding.etEmailSignUp.text.toString(),
                    binding.etPasswordSignUp.text.toString()
                )

                // Lấy danh sách người dùng từ SharedPreferences
                val userListJson = sharedPreferences.getString("USER_LIST", null)
                val type = object : TypeToken<ArrayList<User>>() {}.type
                val userList: ArrayList<User> =
                    Gson().fromJson(userListJson, type) ?: ArrayList()

                // Thêm người dùng mới vào danh sách
                userList.add(newUser)

                // Lưu danh sách người dùng mới vào SharedPreferences
                val editor = sharedPreferences.edit()
                val jsonUserList = Gson().toJson(userList)
                editor.putString("USER_LIST", jsonUserList)
                editor.apply()

                // Chuyển đến MainActivity
                val intentActivity = Intent(this, MainActivity::class.java)
                startActivity(intentActivity)
                finish()
            }
        }
    }
}