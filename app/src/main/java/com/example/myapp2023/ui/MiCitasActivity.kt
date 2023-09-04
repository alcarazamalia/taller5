package com.example.myapp2023.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2023.R
import com.example.myapp2023.util.PreferenceHelper

class MiCitasActivity : AppCompatActivity() {

    private lateinit var adapterMisCitas: AdapterMisCitas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_citas)

        val rvMisCitas = findViewById<RecyclerView>(R.id.rv_mis_citas)

        val citas = PreferenceHelper.getCitas(this)
        adapterMisCitas = AdapterMisCitas(this, citas)

        rvMisCitas.layoutManager = LinearLayoutManager(this)
        rvMisCitas.adapter = adapterMisCitas
    }
}