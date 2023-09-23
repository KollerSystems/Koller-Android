package com.norbert.koller.shared

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class BaseViewModel(pagingSource: ()-> BasePagingSource) : ViewModel() {

    companion object{
        const val pageSize : Int = 25
    }

    val pagingData: Flow<PagingData<Any>> = Pager(config = PagingConfig(
        pageSize = pageSize,
        initialLoadSize = pageSize,
        prefetchDistance = 1
    )) {
        pagingSource()
    }.flow



}