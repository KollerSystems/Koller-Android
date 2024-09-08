package com.norbert.koller.shared.api

import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.managers.CacheManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        CacheManager.loginData?.accessToken.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }


        return chain.proceed(requestBuilder.build())
    }
}