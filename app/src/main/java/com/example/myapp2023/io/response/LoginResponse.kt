package com.example.myapp2023.io.response

import com.example.myapp2023.model.User

data class LoginResponse(
    val id_user: Int,
    val name: String,
    val accessToken: String,
    val expiration_date: String

)
