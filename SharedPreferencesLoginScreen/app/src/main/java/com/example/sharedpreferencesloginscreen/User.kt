package com.example.sharedpreferencesloginscreen

import java.io.Serializable

data class User(
    val email: String,
    val passWord: String
) : Serializable
