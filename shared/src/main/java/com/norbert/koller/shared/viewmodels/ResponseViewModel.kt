package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.FullScreenLoading
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UsersFragment
import com.norbert.koller.shared.managers.CacheManager

class ResponseViewModel : ViewModel() {

    var id: Int? = null
    var response = MutableLiveData<Any>()
    var owner : UserData? = null

    fun load(api : suspend () -> retrofit2.Response<*>) {
        RetrofitInstance.communicate(viewModelScope, api,
            {
                onLoadSuccess(it as BaseData)
            },
            { _, _ ->
                onLoadError()
            }
        )
    }

    fun refresh(api : suspend () -> retrofit2.Response<*>){
        RetrofitInstance.communicate(viewModelScope, api,
            {
                onRefreshSuccess(it as BaseData)
            },
            {_, _ ->
                onRefreshError()
            }
        )
    }

    lateinit var onLoadError: () -> Unit
    lateinit var onLoadSuccess: (response : BaseData) -> Unit

    lateinit var onRefreshError: () -> Unit
    lateinit var onRefreshSuccess: (response : BaseData) -> Unit
}