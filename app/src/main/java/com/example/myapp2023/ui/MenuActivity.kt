package com.example.myapp2023.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapp2023.R
import com.example.myapp2023.io.ApiService
import com.example.myapp2023.util.PreferenceHelper
import com.example.myapp2023.util.PreferenceHelper.set
import com.example.myapp2023.util.PreferenceHelper.get
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {
    private val apiService by lazy {
        ApiService.create()
    }
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnReservarCita = findViewById<Button>(R.id.btn_reservar_cita)
        btnReservarCita.setOnClickListener{
            goToCreateCita()
        }
        val btnMiCitas = findViewById<Button>(R.id.btn_mi_citas)
        btnMiCitas.setOnClickListener{
            goToMiCitas()
        }
        val btnUbicacion = findViewById<Button>(R.id.btn_ubicacion)
        btnUbicacion.setOnClickListener{
            goToUbicacion()
        }
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        btnLogout.setOnClickListener{
            performLogout()
        }
    }
    private fun goToUbicacion(){
        val i = Intent( this, UbicacionActivity::class.java)
        startActivity(i)
    }
    private fun goToCreateCita(){
        val i = Intent( this, CreateCitaActivity::class.java)
        startActivity(i)
    }
    private fun goToLogin(){
        val i = Intent( this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
    private fun goToMiCitas(){
        val i = Intent( this, MiCitasActivity::class.java)
        startActivity(i)
    }
    private fun performLogout() {
        val accessToken = preferences["accessToken", ""]
        val call = apiService.postLogout("Bearer $accessToken")
        call.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSesionPreference()
                goToLogin()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext, "Se produjo un error en el servidor",
                    Toast.LENGTH_SHORT).show()
            }

        } )
    }
    private fun clearSesionPreference(){
        preferences["sesion"] = ""
    }
}