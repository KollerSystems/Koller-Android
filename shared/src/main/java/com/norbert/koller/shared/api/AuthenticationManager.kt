package com.norbert.koller.shared.api

import android.app.Activity
import com.norbert.koller.shared.data.ApiLoginRefreshData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.managers.ApplicationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthenticationManager : Authenticator {


    @Synchronized // annotate with @Synchronized to force parallel threads/coroutines to block and wait in an ordered manner when accessing authenticate()
    override fun authenticate(route: Route?, response: Response): Request? {

        // prevent parallel refresh requests
        val accessToken = LoginTokensData.instance?.accessToken
        val alreadyRefreshed = response.request.header("Authorization")?.contains(accessToken.toString(), true) == false
        if (alreadyRefreshed) { // if request's header's token is different, then that means the access token has already been refreshed and we return the response with the locally persisted token in the header
            return response.request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        }

        // logic to handle refreshing the token


        val tokens = ApiLoginRefreshData("refresh_token", LoginTokensData.instance!!.refreshToken)
        var request : Request? = null

        runBlocking(Dispatchers.IO) {
            RetrofitInstance.communicate({RetrofitInstance.api.postLoginTokens(tokens)}, {it as LoginTokensResponseData
                request = response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
            ) {error, errorBody ->
                if(errorBody?.error == "A valid refresh token is required!"){
                    (ApplicationManager.currentContext!! as Activity).finishAffinity()
                    ApplicationManager.openActivity(ApplicationManager.currentContext!!, ApplicationManager.loginActivity()::class.java)
                }
            }

            return@runBlocking
        }.let {
            return request
        }

    }

}