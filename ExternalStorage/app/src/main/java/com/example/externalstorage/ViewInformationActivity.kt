package com.example.externalstorage

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ViewInformationActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_information)

        // findViewById returns a view, we need to cast it into TextView
        textView = findViewById(R.id.textView_get_saved_data)
    }

    fun showPublicData(view: View) {
        // Accessing the saved data from the downloads folder
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        // geeksData represent the file data that is saved publicly
        val file = File(folder, "testData.txt")
        val data = getData(file)
        if (data != null) {
            textView.text = data
        } else {
            textView.text = "No Data Found"
        }
    }

    fun showPrivateData(view: View) {
        // GeeksForGeeks represent the folder name to access privately saved data
        val folder = getExternalFilesDir("ExternalStorage")

        // gft.txt is the file that is saved privately
        val file = File(folder, "external.txt")
        val data = getData(file)
        if (data != null) {
            textView.text = data
        } else {
            textView.text = "No Data Found"
        }
    }

    fun back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    // getData() is the method which reads the data
    // the data that is saved in byte format in the file
    private fun getData(myfile: File): String? {
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(myfile)
            var i: Int
            val buffer = StringBuffer()
            while (fileInputStream.read().also { i = it } != -1) {
                buffer.append(i.toChar())
            }
            return buffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileInputStream?.close()
        }
        return null
    }
}
