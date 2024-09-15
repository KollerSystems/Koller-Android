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
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.DataStoreManager.Companion.createDynamicUserDataStore
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

abstract class LoginViewModel : ViewModel() {


    val loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val userData : MutableLiveData<UserData> = MutableLiveData()
    var getRoleError : MutableLiveData<Boolean> = MutableLiveData()
    var getUserError : MutableLiveData<String?> = MutableLiveData()
    var postLoginError : MutableLiveData<String?> = MutableLiveData()

    abstract fun correctRole(role : Int) : Boolean

    fun login(loginData : ApiLoginUsernameAndPasswordData){

        postLoginError.value = null

        loading.value = true


        RetrofitInstance.communicate(viewModelScope, {RetrofitInstance.api.postLogin(loginData)},
            {
                it as LoginTokensResponseData
                CacheManager.loginData = LoginTokensData(it.accessToken, Calendar.getInstance().timeInMillis + it.expiresIn-RetrofitInstance.timeout, it.refreshToken)

                RetrofitInstance.communicate(viewModelScope, RetrofitInstance.api::getCurrentUser,
                    { response ->
                        response as UserData
                        if(correctRole(response.role!!)){
                            CacheManager.loginData!!.uid = response.uid
                            userData.value = response
                        }
                        else{
                            getRoleError.value = true
                            loading.value = false
                        }
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