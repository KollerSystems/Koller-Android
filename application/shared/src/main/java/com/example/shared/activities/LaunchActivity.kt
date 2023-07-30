package com.example.shared.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.shared.R
import com.example.shared.DataStoreManager
import com.example.shared.MyApplication
import com.example.shared.api.APIInterface
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.ApiLoginRefreshData
import com.example.shared.data.ApiLoginTokensData
import com.example.shared.data.UserData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (UserData.instance.UID != -1) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )

        lifecycleScope.launch {
            val refreshToken = DataStoreManager.read(this@LaunchActivity, DataStoreManager.REFRESH_TOKEN_NAME)
            if(refreshToken == null){
                MyApplication.openLogin.invoke(this@LaunchActivity)
                finish()
            }
            else{
                val loginResponse = RetrofitHelper.buildService(APIInterface::class.java)
                loginResponse.postLogin(ApiLoginRefreshData("refresh_token", refreshToken)).enqueue(
                    object : Callback<ApiLoginTokensData> {
                        override fun onResponse(
                            call: Call<ApiLoginTokensData>,
                            loginResponse: Response<ApiLoginTokensData>
                        ) {
                            if(loginResponse.code() == 200) {

                                ApiLoginTokensData.instance = loginResponse.body()!!

                                lifecycleScope.launch {
                                    DataStoreManager.save(this@LaunchActivity, DataStoreManager.REFRESH_TOKEN_NAME, ApiLoginTokensData.instance.refresh_token)
                                }


                                val userResponse = RetrofitHelper.buildService(APIInterface::class.java)
                                userResponse.getCurrentUser(APIInterface.getHeaderMap()).enqueue(
                                    object : Callback<UserData> {
                                        override fun onResponse(
                                            call: Call<UserData>,
                                            userResponse: Response<UserData>
                                        ) {
                                            if(userResponse.code() == 200) {

                                                UserData.instance = userResponse.body()!!

                                                MyApplication.openMain.invoke(this@LaunchActivity)
                                                finish()
                                            }
                                            else{
                                                MyApplication.openLogin.invoke(this@LaunchActivity)
                                                finish()
                                            }
                                        }

                                        override fun onFailure(call: Call<UserData>, t: Throwable) {
                                            MyApplication.openLogin.invoke(this@LaunchActivity)
                                            finish()
                                        }
                                    }
                                )
                            }
                            else{
                                MyApplication.openLogin.invoke(this@LaunchActivity)
                                finish()
                            }

                        }

                        override fun onFailure(call: Call<ApiLoginTokensData>, t: Throwable) {
                            MyApplication.openLogin.invoke(this@LaunchActivity)
                            finish()
                        }
                    }
                )
            }
        }


    }
}