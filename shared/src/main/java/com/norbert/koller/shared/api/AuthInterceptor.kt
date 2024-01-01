package com.norbert.koller.shared.api

import android.content.Context
import com.norbert.koller.shared.data.ApiLoginTokensData
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    private val sessionManager = SessionManager()

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        /*val accessToken = sessionManager.getAccessToken()

        if (accessToken != null && sessionManager.isAccessTokenExpired()) {
            val refreshToken = sessionManager.getRefreshToken()

            // Make the token refresh request
            val refreshedToken = runBlocking {
                val response = apiService.refreshAccessToken()
                // Update the refreshed access token and its expiration time in the session
                sessionManager.updateAccessToken(response.accessToken, response.expiresIn)
                response.accessToken
            }

            if (refreshedToken != null) {
                // Create a new request with the refreshed access token
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken")
                    .build()

                // Retry the request with the new access token
                return chain.proceed(newRequest)
            }
        }

        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()





        ApiLoginTokensData.instance?.accessToken.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }*/

        val requestBuilder = chain.request().newBuilder()
        return chain.proceed(requestBuilder.build())
    }
}