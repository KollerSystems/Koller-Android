package com.norbert.koller.shared.data

data class ApiLoginData (
    val grant_type : String,
    val username : String,
    val password : String
    )