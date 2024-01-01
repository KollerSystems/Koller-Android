package com.norbert.koller.shared.api

import com.norbert.koller.shared.data.LoginTokensData
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        LoginTokensData.instance?.accessToken.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }


        return chain.proceed(requestBuilder.build())
    }
}