package com.example.shared

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.shared.recycleradapter.BaseRecycleAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BaseViewModel(pagingSource: ()-> BasePagingSource) : ViewModel() {

    companion object{
        val pageSize : Int = 25
    }

    val pagingData: Flow<PagingData<Any>> = Pager(config = PagingConfig(
        pageSize = pageSize,
        initialLoadSize = pageSize,
        prefetchDistance = 1
    )) {
        pagingSource()
    }.flow

}