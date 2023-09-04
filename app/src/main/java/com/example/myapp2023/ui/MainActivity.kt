package com.example.myapp2023.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapp2023.R
import com.example.myapp2023.io.ApiService
import com.example.myapp2023.io.response.LoginRequest
import com.example.myapp2023.io.response.LoginResponse
import retrofit2.Call
import retrofit2.Response
import com.example.myapp2023.util.PreferenceHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val apiService: ApiService by lazy {
        ApiService.create()
    }
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }
    companion object {
        private const val REQUEST_WRITE_STORAGE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkWritePermission()

        val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences.getString("accessToken", "")?.contains(".")==true)
           goToMenu()

        val tvGoRegister = findViewById<TextView>(R.id.tv_go_to_register)
        tvGoRegister.setOnClickListener {
            goToRegister()
        }
        val btnGoMenu = findViewById<Button>(R.id.btn_go_to_menu)
        btnGoMenu.setOnClickListener {
            performLogin()
        }
    }
    private fun checkWritePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_STORAGE)
        } else {
            performWriteOperations()
        }
    }
    private fun performWriteOperations() {
        val directory = getExternalFilesDir(null)
        val file = File(directory, "perfa_okhttp.dm")

        try {
            val outputStream = FileOutputStream(file)
            val text = "Hola, mundo!"
            outputStream.write(text.toByteArray())
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun goToRegister(){
        val i = Intent( this, RegisterActivity::class.java)
        startActivity(i)
    }
    private fun goToMenu(){
        val i = Intent( this, MenuActivity::class.java)
        startActivity(i)
        finish()
    }
    private fun createSesionPreference(accessToken: String){
        preferences.edit().putString("accessToken", accessToken).apply()
        Toast.makeText(applicationContext,""+accessToken, Toast.LENGTH_SHORT).show()
    }

    private fun performLogin() {
        val etEmail = findViewById<EditText>(R.id.et_email).text.toString()
        val etPassword = findViewById<EditText>(R.id.et_password).text.toString()

        if (etEmail.trim().isEmpty() || etPassword.trim().isEmpty()){
            Toast.makeText(applicationContext, "Debe ingresar su correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val request = LoginRequest(etEmail, etPassword)
        val call = apiService.postLogin(request)

        call.enqueue(object: retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                try {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {

                            Toast.makeText(applicationContext, "Inicion de sesion correcto", Toast.LENGTH_SHORT).show()
                            goToMenu()
                            createSesionPreference(loginResponse.accessToken)

                        } else {
                            Toast.makeText(applicationContext, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Se produjo un error en el servidor", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    performWriteOperations()

                } else {

                }
            }
        }
    }
}