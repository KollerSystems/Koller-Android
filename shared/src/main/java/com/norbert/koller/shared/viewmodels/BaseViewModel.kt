package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapters.PagingSource
import kotlinx.coroutines.flow.Flow

class BaseViewModel : ViewModel() {

    companion object{
        const val pageSize : Int = 25
    }

    var owner : UserData? = null

    var id : Int? = null

    var filters : MutableMap<String, ArrayList<String>> = mutableMapOf()
    var dateFilters : MutableMap<String, androidx.core.util.Pair<Long, Long>> = mutableMapOf()

    var filtersShown : MutableLiveData<Boolean> = MutableLiveData(false)

    lateinit var pagingSource: (()-> PagingSource)
    lateinit var currentPagingSource : PagingSource

    val pagingData: Flow<PagingData<Any>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = pageSize/2)
        ) {
            currentPagingSource = pagingSource()
            currentPagingSource
        }.flow.cachedIn(viewModelScope)

    init {

    }
}