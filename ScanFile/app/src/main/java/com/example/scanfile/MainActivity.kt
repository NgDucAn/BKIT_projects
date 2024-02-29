package com.example.scanfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanfile.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1
    private var isScanning = false
    private lateinit var listFile: ArrayList<FileInfo>
    private lateinit var rvListFile: RecyclerView
    private lateinit var listFilesAdapter: ListFilesAdapter
    private val REQUEST_MANAGE_ALL_FILES_ACCESS = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btStart.setOnClickListener() {
                when (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )) {
                    PackageManager.PERMISSION_GRANTED -> {
                        if (isExternalStorageMounted()) {
                            listFile = ArrayList<FileInfo>()
                            listFilesAdapter = ListFilesAdapter(this@MainActivity, listFile)
                            isScanning = true
                            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                            rvListFile.layoutManager = linearLayoutManager
                            rvListFile.adapter = listFilesAdapter
                            scanFiles(Environment.getExternalStorageDirectory());
                        } else {
                            println("Không tìm thấy bộ nhớ ngoài")
                        }
                    }
                    else -> {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            READ_EXTERNAL_STORAGE_REQUEST_CODE
                        )
                    }
                }
            }

            btPause.setOnClickListener() {
                isScanning = true
            }
            btStop.setOnClickListener() {
                isScanning = false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {

                if (!Environment.isExternalStorageManager()) {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    )
                    val uri = Uri.parse("package:${applicationContext.packageName}")
                    intent.setData(uri)
                    startActivityIfNeeded(intent, READ_EXTERNAL_STORAGE_REQUEST_CODE)

                } else {
                    listFile = ArrayList()
                    listFilesAdapter = ListFilesAdapter(this@MainActivity, listFile)
                    isScanning = true
                    rvListFile = binding.rvListFile
                    val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                    rvListFile.layoutManager = linearLayoutManager
                    rvListFile.adapter = listFilesAdapter
                    scanFiles(Environment.getExternalStorageDirectory());
                }
            } catch (_: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(intent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_MANAGE_ALL_FILES_ACCESS && Environment.isExternalStorageManager()) {
            if (isExternalStorageMounted()) {
            } else {
                println("Không tìm thấy bộ nhớ ngoài")
            }
        }
    }
    private fun scanFiles(directory: File) {
        CoroutineScope(Dispatchers.Default).launch {
            val stack = Stack<File>()
            stack.push(directory)
            while (!stack.empty()){
                if (isScanning) {
                    val currentDir = stack.pop()
                    if (currentDir.isDirectory) {
                        val files = currentDir.listFiles()
                        if (files != null) {
                            for (file in files) {
                                if (file.isDirectory) {
                                    stack.push(file)
                                } else {
                                    delay(1000L)
                                    withContext(Dispatchers.Main) {
                                        val fileType = getFileType(file)
                                        val customFile = FileInfo(fileType, file.absolutePath)
                                        listFile.add(0, customFile)
                                        listFilesAdapter.notifyItemInserted(0)
                                    }
                                    val fileName = file.name
                                    val filePath = file.absolutePath
                                    Log.d("ScanFilePath", "Path: $filePath")
                                    Log.d("ScanFilesActivity", "File: $fileName")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getFileType(file: File?): String {
        val fileType = file?.name?.substringAfterLast(".")
        if (fileType.equals("png", ignoreCase = true)
            || fileType.equals("jpg", ignoreCase = true)) {
            return "picture"
        }
        if (fileType.equals("mp3", ignoreCase = true)
            || fileType.equals("wav", ignoreCase = true)) {
            return "audio"
        }

        if (fileType.equals("txt", ignoreCase = true)) {
            return "doc"
        }
        return "unknown"
    }

    private fun isExternalStorageMounted(): Boolean {
        val dirState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == dirState
    }

}