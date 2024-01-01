package com.norbert.koller.shared.api

class SessionManager {

    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var accessTokenExpirationTime: Long? = null

    fun getAccessToken() : String?{
        return accessToken
    }

    fun getRefreshToken() : String?{
        return refreshToken
    }

    // Method to check if the access token has expired
    fun isAccessTokenExpired(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return accessTokenExpirationTime != null && currentTimeMillis >= accessTokenExpirationTime!!
    }

    // Method to update the access token and its expiration time in the session
    fun updateAccessToken(token: String, expiresIn: Long) {
        accessToken = token
        accessTokenExpirationTime = System.currentTimeMillis() + (expiresIn - RetrofitInstance.timeout) * 1000 // Convert expiresIn to milliseconds
    }

    // Other session management methods
}