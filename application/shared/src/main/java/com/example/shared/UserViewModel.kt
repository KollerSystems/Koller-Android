package com.example.shared

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.shared.api.APIInterface
import com.example.shared.data.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class UserViewModel : ViewModel() {
    private val userPagingSource = UserPagingSource()

    val pagingData: Flow<PagingData<Any>> = Pager(config = PagingConfig(
        pageSize = 25,
        initialLoadSize = 25,
        prefetchDistance = 1
    )) {
        userPagingSource
    }.flow
}