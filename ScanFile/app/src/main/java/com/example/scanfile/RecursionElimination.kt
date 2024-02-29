package com.example.scanfile

import android.os.Environment
import java.io.File

class RecursionElimination {

    fun getAllFilesInfoExternal(): List<FileInfo> {
        val sdCardFileDir = Environment.getExternalStorageDirectory()
        val fileList = mutableListOf<FileInfo>()

        sdCardFileDir?.let {
            getAllFilesInfo(it, fileList)
        }

        return fileList
    }

    private fun getAllFilesInfo(directory: File, fileList: MutableList<FileInfo>) {
        val files = directory.listFiles()

        files?.let {
            for (file in it) {
                if (file.isDirectory) {
                    getAllFilesInfo(file, fileList)
                } else {

                }
            }
        }
    }
}
