package com.example.internalstoragedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.internalstoragedemo.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //private val fileName = "note.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSave.setOnClickListener() {
            saveData(binding.etInput.text.toString())
        }

        binding.btRead.setOnClickListener() {
            readData()
        }
    }

    private val fileName = "note.txt"
    private fun saveData(data: String) {
        try {
            val out: FileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
            out.write(data.toByteArray())
            out.close()
            Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error:" + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun readData() {
        try {
            val input: FileInputStream = openFileInput(fileName)
            val br = BufferedReader(InputStreamReader(input))
            val sb = StringBuilder()
            var s: String?
            while ((br.readLine().also { s = it }) != null) {
                sb.append(s).append("\n")
            }
            binding.tvOutput.text = sb.toString()
        } catch (e: Exception) {
            Toast.makeText(this, "Error:" + e.message, Toast.LENGTH_SHORT).show()
        }
    }
}