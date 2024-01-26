package com.example.loginscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginscreen.databinding.ActivitySignUpBinding
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var listUser: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAlreadyHaveAnAccount.setOnClickListener() {
            val intentMainActivity = Intent(this, MainActivity::class.java)
            startActivity(intentMainActivity)
        }

        binding.btSignUp.setOnClickListener() {
            val email: String = binding.etEmailSignUp.text.toString()
            val passWord: String = binding.etPasswordSignUp.text.toString()
            val user = User(email, passWord)
            readUsersFromFile()
            listUser.add(user)
            for (u in listUser) {
                Log.d("SignUpActivity", "User: Email=${u.email}, Password=${u.passWord}")
            }
            saveUser(user)
            Toast.makeText(applicationContext, "Dang ki thanh cong", Toast.LENGTH_SHORT).show()
            val intentMainActivity = Intent(this, MainActivity::class.java)
            intentMainActivity.putExtra("listUser", listUser)
            startActivity(intentMainActivity)
            binding.etEmailSignUp.text.clear()
            binding.etPasswordSignUp.text.clear()
        }
    }

    private fun saveUser(user: User) {
        val file = "userDetail.txt"
        val data = "Email:${user.email}\nPassword:${user.passWord}\n"
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(file, Context.MODE_APPEND)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readUsersFromFile(): ArrayList<User> {
        val file = "userDetail.txt"
        try {
            val fileInputStream = openFileInput(file)
            val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
            listUser.clear() // Xóa danh sách cũ trước khi đọc mới
            var line: String?
            var email = ""
            var password: String
            while (bufferedReader.readLine().also { line = it } != null) {
                if (line?.startsWith("Email:") == true) {
                    email = line!!.substringAfter("Email:").trim()
                } else if (line?.startsWith("Password:") == true) {
                    password = line!!.substringAfter("Password:").trim()
                    listUser.add(User(email, password))
                }
            }
            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return listUser
    }
}
