package com.norbert.koller.shared.recycleradapter

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.norbert.koller.shared.api.UserPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class BaseViewModel(pagingSource: (()-> BasePagingSource)) : ViewModel() {


    companion object{
        const val pageSize : Int = 25
    }



    val pagingData: Flow<PagingData<Any>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 2)
        ) {
        pagingSource()
    }.flow
}