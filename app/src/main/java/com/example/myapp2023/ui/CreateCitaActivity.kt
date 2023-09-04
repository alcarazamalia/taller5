package com.example.myapp2023.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.myapp2023.R
import com.example.myapp2023.model.CitasMedicas
import com.example.myapp2023.util.PreferenceHelper
import java.util.Calendar
import java.util.UUID

class CreateCitaActivity : AppCompatActivity() {
    private lateinit var preferenceHelper: PreferenceHelper

    private val selectedCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cita)

        preferenceHelper = PreferenceHelper

        val spinnerEspecialidades = findViewById<Spinner>(R.id.spinner_especialidades)
        val spinnerMedico = findViewById<Spinner>(R.id.spinner_medico)
        val etScheduledDate = findViewById<EditText>(R.id.et_fecha)

        val btnNext = findViewById<Button>(R.id.btn_siguiente)
        val btnConfirm = findViewById<Button>(R.id.btn_confirmar)
        val cvNext = findViewById<CardView>(R.id.cv_siguiente)
        val cvConfirm = findViewById<CardView>(R.id.cv_confirmar)

        btnNext.setOnClickListener{
            cvNext.visibility = View.GONE
            cvConfirm.visibility = View.VISIBLE
        }

        btnConfirm.setOnClickListener{
            val especialidad = spinnerEspecialidades.selectedItem as String
            val medico = spinnerMedico.selectedItem as String
            val fecha = etScheduledDate.text.toString()
            val hora = getSelectedHourFromRadioGroup()
            val id = UUID.randomUUID().toString().toInt()

            val cita = CitasMedicas( id, especialidad, medico, fecha, hora)
            preferenceHelper.saveCita(this, cita)
            Toast.makeText(applicationContext, "Cita realizada exitosamente", Toast.LENGTH_SHORT).show()
            finish()
        }

        val opcionEspecialidades = arrayOf("Clinica Medica", "Dermatologia", "Gastoenterologia", "Ginecologia", "Proctologia")
        spinnerEspecialidades.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcionEspecialidades)

        val opcionMedico = arrayOf("Sommer", "Sterson", "Darquin", "Kido", "Ponce")
        spinnerMedico.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcionMedico)
    }
    fun onClickScheduledDate( v: View){
        val etScheduledDate = findViewById<EditText>(R.id.et_fecha)

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener{ DatePicker, y, m, d ->
            selectedCalendar.set(y, m, d)
            etScheduledDate.setText("$y - $m -$d")
            displayRadioButtons()
        }
        DatePickerDialog (this, listener, year, month, dayOfMonth).show()

    }
    private fun displayRadioButtons(){
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.clearCheck()
        radioGroup.removeAllViews()

        val hours = arrayOf("8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM")
        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it
            radioGroup.addView(radioButton)
        }
    }
    private fun getSelectedHourFromRadioGroup(): String {
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(checkedRadioButtonId)
        return radioButton.text.toString()
    }
}