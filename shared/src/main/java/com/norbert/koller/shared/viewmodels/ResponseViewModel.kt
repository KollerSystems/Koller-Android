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

    companion object {
        const val LOADING = 0
        const val ERROR = 1
        const val NONE = 2
    }

    var state : Int = LOADING

    var id: Int? = null
    var response = MutableLiveData<Any>()
    var owner : UserData? = null

    fun updateValues(it : BaseData, dataTag: String){
        it as BaseData
        it.saveReceivedTime()
        CacheManager.savedValues[Pair(dataTag, it.getMainID())] = it
        response.value = it
    }

    fun load(api : suspend () -> retrofit2.Response<*>, dataTag :String) {
        state = LOADING
        RetrofitInstance.communicate(viewModelScope, api,
            {
                state = NONE
                it as BaseData
                updateValues(it, dataTag)
                onLoadSuccess(it)
            },
            { _, _ ->
                state = ERROR
                onLoadError()
            }
        )
    }

    fun refresh(api : suspend () -> retrofit2.Response<*>, dataTag :String){
        RetrofitInstance.communicate(viewModelScope, api,
            {
                it as BaseData
                updateValues(it, dataTag)
                onRefreshSuccess(it)
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