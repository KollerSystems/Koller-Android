package com.norbert.koller.shared.data

data class LoginTokensData (
    val accessToken : String,
    val expiresAt : Long,
    val refreshToken : String
){
    companion object{
        var instance : LoginTokensData? = null
    }
}