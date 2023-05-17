package com.example.koller.api

data class ApiLoginData (
    val grant_type : String,
    val username : String,
    val password : String
    )