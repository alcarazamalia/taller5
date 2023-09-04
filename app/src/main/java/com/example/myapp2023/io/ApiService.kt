package com.example.myapp2023.io

import com.example.myapp2023.io.response.LoginRequest
import com.example.myapp2023.io.response.LoginResponse
import com.example.myapp2023.io.response.RegisterRequest
import com.example.myapp2023.io.response.RegisterResponse
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header

interface ApiService {
    @POST ("b1/api/sesion")
    fun postLogin(
        @Body request: LoginRequest):
            Call<LoginResponse>

    @POST ("b1/api/usuarios")
    fun postRegister(
        @Body request: RegisterRequest):
            Call<RegisterResponse>


    @POST( value = "logout")
    fun postLogout(
        @Header(value = "Authorization") authHeader: String):
            Call<Void>

    companion object Factory {
        private const val BASE_URL = "http://turnostaller.dnsalias.com:28090/"
        fun create():ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}