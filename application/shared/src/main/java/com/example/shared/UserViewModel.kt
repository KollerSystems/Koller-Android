package com.example.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.api.APIInterface
import com.example.shared.data.UserData
import com.example.shared.recycleradapter.UserRecycleAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class UserViewModel(recyclerAdapter: UserRecycleAdapter) : ViewModel() {

    lateinit var recyclerAdapterr : UserRecycleAdapter

    companion object{
        val pageSize : Int = 10
    }

    init{
        recyclerAdapterr = recyclerAdapter
        recyclerAdapter.onRetryClick = {
            retry()
        }
    }

    private var userPagingSource = UserPagingSource(recyclerAdapter)

    val pagingData: Flow<PagingData<Any>> = Pager(config = PagingConfig(
        pageSize = pageSize,
        initialLoadSize = pageSize,
        prefetchDistance = 1
    )) {
        UserPagingSource(recyclerAdapter)
    }.flow

    fun retry() {

        userPagingSource = UserPagingSource(recyclerAdapterr)
        userPagingSource.invalidate()

    }
}