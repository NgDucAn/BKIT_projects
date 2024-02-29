package com.example.externalstorage

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    // After API 23 the permission request for accessing external storage is changed
    // Before API 23 permission request is asked by the user during installation of app
    // After API 23 permission request is asked at runtime
    private val EXTERNAL_STORAGE_PERMISSION_CODE = 23
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // findViewById returns a view, we need to cast it to EditText View
        editText = findViewById(R.id.editText_data)
    }

    fun savePublicly(view: View) {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            EXTERNAL_STORAGE_PERMISSION_CODE
        )
        val editTextData = editText.text.toString()
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(folder, "testData.txt")
        writeTextData(file, editTextData)
        editText.setText("")
    }

    fun savePrivately(editTextData: String) {
        val folder = getExternalFilesDir("externalStorage")
        val file = File(folder, "external.txt")

        writeTextData(file, editTextData)
        editText.setText("")
    }

    fun viewInformation() {
        // Creating an intent to start a new activity
        val intent = Intent(this, ViewInformationActivity::class.java)
        startActivity(intent)
    }

    // writeTextData() method save the data into the file in byte format
    // It also toasts a message "Done/filepath_where_the_file_is_saved"
    private fun writeTextData(file: File, data: String) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(data.toByteArray())
            Toast.makeText(this, "Done" + file.absolutePath, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutputStream?.close()
        }
    }
}

