package com.example.loginscreen

import java.io.Serializable

data class User(
    val email: String,
    val passWord: String
) : Serializable
