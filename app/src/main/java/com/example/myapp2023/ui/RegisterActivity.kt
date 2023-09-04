package com.example.myapp2023.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapp2023.R
import com.example.myapp2023.io.ApiService
import com.example.myapp2023.io.response.RegisterRequest
import com.example.myapp2023.io.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private val apiService by lazy {
        ApiService.create()
    }
    private lateinit var etNombre: TextView
    private lateinit var etApellido: TextView
    private lateinit var etEmail: TextView
    private lateinit var etDNI: TextView
    private lateinit var etTelefono: TextView
    private lateinit var etPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etEmail= findViewById(R.id.etEmail)
        etDNI = findViewById(R.id.etDNI)
        etTelefono = findViewById(R.id.etTelefono)
        etPassword = findViewById(R.id.etPassword)

        val btnRegister = findViewById<Button>(R.id.btn_go_to_menu)
        btnRegister.setOnClickListener {
            registrarUsuario()
        }
        val tvGoLogin = findViewById<TextView>(R.id.tv_go_to_login)
        tvGoLogin.setOnClickListener {
            goToLogin()
        }
    }
    private fun registrarUsuario() {
        val name = etNombre.text.toString()
        val lastname = etApellido.text.toString()
        val email = etEmail.text.toString()
        val dni = etDNI.text.toString().toInt()
        val phone = etTelefono.text.toString().toLong()
        val password = etPassword.text.toString()

        val request = RegisterRequest(name, password, email, lastname, dni, phone)

        apiService.postRegister(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    goToLogin()
                }else {
                    val errorMessage =response.errorBody()?.string()
                    val errorText = errorMessage ?: " "
                    Toast.makeText(this@RegisterActivity, errorText, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                val errorMessage = " ${t.message}"
                Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun goToLogin(){
        val i = Intent( this, MainActivity::class.java)
        startActivity(i)
    }
}