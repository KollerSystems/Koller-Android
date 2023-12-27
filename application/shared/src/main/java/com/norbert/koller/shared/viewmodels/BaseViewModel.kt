package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.norbert.koller.shared.recycleradapter.BasePagingSource
import kotlinx.coroutines.flow.Flow

class BaseViewModel : ViewModel() {


    companion object{
        const val pageSize : Int = 25
    }

    lateinit var pagingSource: (()-> BasePagingSource)

    val pagingData: Flow<PagingData<Any>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 2)
        ) {
        pagingSource()
    }.flow.cachedIn(viewModelScope)
}