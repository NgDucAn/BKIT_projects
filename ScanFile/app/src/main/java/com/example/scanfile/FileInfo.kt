package com.example.scanfile

import java.io.File

class FileInfo(val fileType: String, val path: String) {
    private val file = File(path)

    fun getName(): String {
        return file.name
    }

    fun getFileSize(): Long {
        return file.length()
    }
}