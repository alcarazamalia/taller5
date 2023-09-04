package com.example.myapp2023.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.myapp2023.model.CitasMedicas
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferenceHelper {
    private const val KEY_CITAS = "key_citas"
    fun defaultPrefs(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit().putString(key, value).apply()
            is Int -> edit().putInt(key, value).apply()
            is Boolean -> edit().putBoolean(key, value).apply()
            is Float -> edit().putFloat(key, value).apply()
            is Long -> edit().putLong(key, value).apply()
            else -> throw UnsupportedOperationException("Tipo de dato no soportado")
        }
    }

    operator fun SharedPreferences.get(key: String, defaultValue: Any? = null): Any? {
        return when (defaultValue) {
            is String? -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            else -> throw UnsupportedOperationException("Tipo de dato no soportado")
        }
    }

    fun saveCita(context: Context, cita: CitasMedicas) {
        val prefs = defaultPrefs(context)
        val citasJson = prefs.getString(KEY_CITAS, null)
        val citasList = citasJson?.let {
            Gson().fromJson<MutableList<CitasMedicas>>(
                it,
                object : TypeToken<MutableList<CitasMedicas>>() {}.type
            )
        } ?: mutableListOf()

        citasList.add(cita)

        val newCitasJson = Gson().toJson(citasList)
        prefs[KEY_CITAS] = newCitasJson
    }
    fun getCitas(context: Context): List<CitasMedicas> {
        val prefs = defaultPrefs(context)
        val citasJson = prefs.getString(KEY_CITAS, null)
        return citasJson?.let {
            Gson().fromJson<List<CitasMedicas>>(
                it,
                object : TypeToken<List<CitasMedicas>>() {}.type
            )
        } ?: emptyList()
    }
}

