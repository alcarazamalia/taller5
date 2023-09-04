package com.example.myapp2023.io.response

data class RegisterRequest(
    val name: String,
    val password: String,
    val email: String,
    val lastname: String,
    val dni: Int,
    val phone: Long
)
