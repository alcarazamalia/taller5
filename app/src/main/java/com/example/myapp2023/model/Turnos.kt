package com.example.myapp2023.model

data class Turnos(
    val id_turno : Int,
    val id_usuario : Int,
    val fecha: String,
    val hora : String,
    val medico : String,
    val estado: Int
)
