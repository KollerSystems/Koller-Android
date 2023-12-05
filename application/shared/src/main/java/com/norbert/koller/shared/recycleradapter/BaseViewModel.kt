package com.norbert.koller.shared.recycleradapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow

class BaseViewModel(pagingSource: ()-> BasePagingSource) : ViewModel() {



    companion object{
        const val pageSize : Int = 25
        val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    val pagingData: Flow<PagingData<Any>> = Pager(
        config = PagingConfig(
        pageSize = pageSize,
        initialLoadSize = pageSize,
        prefetchDistance = 2
    )) {
        pagingSource()
    }.flow.cachedIn(BaseViewModel.viewModelScope)



}