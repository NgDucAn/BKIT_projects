package com.example.noteapp2

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val id: Int = 0,
    var title: String = "",
    var content: String = "",
    var editTime: String? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
) : Serializable
