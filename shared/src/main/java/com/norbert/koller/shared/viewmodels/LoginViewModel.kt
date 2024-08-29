package com.norbert.koller.shared.viewmodels

import android.icu.util.Calendar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.ApiLoginUsernameAndPasswordData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.DataStoreManager
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {


    val loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val userData : MutableLiveData<UserData> = MutableLiveData()
    var getUserError : MutableLiveData<String?> = MutableLiveData()
    var postLoginError : MutableLiveData<String?> = MutableLiveData()

    fun login(loginData : ApiLoginUsernameAndPasswordData){

        postLoginError.value = null

        loading.value = true


        RetrofitInstance.communicate(viewModelScope, {RetrofitInstance.api.postLogin(loginData)},
            {
                it as LoginTokensResponseData
                LoginTokensData.instance = LoginTokensData(it.accessToken, Calendar.getInstance().timeInMillis + it.expiresIn-RetrofitInstance.timeout, it.refreshToken)

                RetrofitInstance.communicate(viewModelScope, RetrofitInstance.api::getCurrentUser,
                    { response ->
                        response as UserData
                        LoginTokensData.instance!!.uid = response.uid
                        userData.value = response
                    },
                    {errorMsg, _ ->
                        getUserError.value = errorMsg?: "-"
                        loading.value = false
                    })

            },
            {_, errorBody ->

                postLoginError.value = errorBody?.error?: "-"
                loading.value = false
            })
    }

}