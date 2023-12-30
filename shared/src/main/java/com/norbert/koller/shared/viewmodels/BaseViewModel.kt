package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import kotlinx.coroutines.flow.Flow

class BaseViewModel : ViewModel() {

    companion object{
        const val pageSize : Int = 25
    }

    var ID = -1

    var filters : MutableMap<String, ArrayList<String>> = mutableMapOf()
    var dateFilters : MutableMap<String, androidx.core.util.Pair<Long, Long>> = mutableMapOf()

    //var sort : Boolean = false

    lateinit var pagingSource: (()-> BasePagingSource)
    lateinit var currentPagingSource : BasePagingSource

    val pagingData: Flow<PagingData<Any>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 2)
        ) {
            currentPagingSource = pagingSource()
            currentPagingSource
        }.flow.cachedIn(viewModelScope)

    init {

    }
}