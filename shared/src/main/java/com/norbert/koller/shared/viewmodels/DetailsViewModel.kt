package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class DetailsViewModel : ViewModel() {

    companion object {
        const val LOADING = 0
        const val ERROR = 1
        const val NONE = 2
    }

    var state : Int = NONE

    var id: Int? = null
    var response = MutableLiveData<Any>()
    var ownerUID : Int = -1

    fun updateValues(it : BaseData, dataType: Class<*>){
        it.saveReceivedTime()
        CacheManager.setDetailsDataMapValue(dataType.simpleName, it.getMainID(), it)
        response.value = it
    }

    fun load(api : suspend () -> retrofit2.Response<*>, dataType :Class<*>) {
        state = LOADING
        viewModelScope.launch {
            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))
            RetrofitInstance.communicate(api,
                {
                    state = NONE
                    it as BaseData
                    updateValues(it, dataType)
                    onLoadSuccess(it)
                },
                { _, _ ->
                    state = ERROR
                    onLoadError()
                }
            )
        }
    }

    fun refresh(api : suspend () -> retrofit2.Response<*>, dataType: Class<*>){
        state = LOADING
        viewModelScope.launch {
            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))
            RetrofitInstance.communicate(api,
                {
                    state = NONE
                    it as BaseData
                    updateValues(it, dataType)
                    onRefreshSuccess(it)
                },
                {_, _ ->
                    state = ERROR
                    onRefreshError()
                }
            )
        }

    }

    lateinit var onLoadError: () -> Unit
    lateinit var onLoadSuccess: (response : BaseData) -> Unit

    lateinit var onRefreshError: () -> Unit
    lateinit var onRefreshSuccess: (response : BaseData) -> Unit
}