package com.example.shared.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val client = OkHttpClient.Builder().build()

    val gyIP =TITOK_HAHAHA
    val grazIP = "http://192.168.0.100/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(grazIP)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}