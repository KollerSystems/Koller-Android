package com.norbert.koller.shared.api

import android.app.Activity
import android.util.Log
import androidx.core.content.ContextCompat
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.shared.data.ApiLoginRefreshData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar
import java.util.concurrent.atomic.AtomicBoolean


class AuthenticationManager : Authenticator {

    private var tokenRefreshInProgress: AtomicBoolean = AtomicBoolean(false)
    private var request: Request? = null

    fun getToken() : ApiLoginRefreshData{
        return ApiLoginRefreshData("refresh_token", LoginTokensData.instance!!.refreshToken)
    }

    override fun authenticate(route: Route?, response: Response): Request? {

        return runBlocking {

            if(!response.request.url.toString().contains("/oauth/token")) {

                Log.d("TESTTESTS", "111")

                if (!tokenRefreshInProgress.get()) {


                    tokenRefreshInProgress.set(true)
                    val response2 : retrofit2.Response<LoginTokensResponseData>  = RetrofitInstance.api.postLoginTokens(getToken())
                    val tokenData : LoginTokensResponseData? = response2.body()

                    if (response2.isSuccessful && tokenData != null) {
                        LoginTokensData.instance = LoginTokensData(
                            tokenData.accessToken,
                            Calendar.getInstance().timeInMillis + tokenData.expiresIn - RetrofitInstance.timeout,
                            tokenData.refreshToken
                        )
                        runBlocking {
                            DataStoreManager.save(
                                ApplicationManager.currentContext!!,
                                LoginTokensData.instance!!
                            )

                        }

                        request = response.request.newBuilder()
                            .header("Authorization", "Bearer ${LoginTokensData.instance?.accessToken}")
                            .build()

                    }
                    else{
                        Log.d("TESTTESTS", "NEM JOPOOOOOOOOOOOOO")

                    }
                    tokenRefreshInProgress.set(false)





                } else {


                    // Waiting for the ongoing request to finish
                    // So that we don't refresh our token multiple times
                    waitForRefresh(response)

                }
            }
            else{

                Log.d("TESTTESTS", "333333")

                tokenRefreshInProgress.set(false)
                val activity = (ApplicationManager.currentContext!! as Activity)
                DataStoreManager.remove(activity, DataStoreManager.TOKENS)


                    activity.runOnUiThread {
                        activity.finishAffinity()
                        ApplicationManager.openActivity(
                            activity,
                            ApplicationManager.loginActivity()::class.java
                        )
                    }


                request = null

            }

            return@runBlocking request

        }

    }

    private suspend fun waitForRefresh(response: Response) {
        while (tokenRefreshInProgress.get()) {
            delay(100)
        }
        request = response.request.newBuilder()
            .header("Authorization", "Bearer ${LoginTokensData.instance!!.accessToken}")
            .build()
    }


    private fun responseCount(response: Response?): Int {
        var result = 1
        while (response?.priorResponse != null && result <= 3) {
            result++
        }
        return result
    }
}