package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.norbert.koller.shared.api.ApiDataObject
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import kotlinx.coroutines.flow.Flow

open class ListViewModel : ViewModel() {

    companion object{
        const val pageSize : Int = 25
    }

    lateinit var apiDataObject: ApiDataObject

    var search : Pair<String, String>? = null

    var state: Int = ApiHelper.STATE_NONE
    var isRequestModeRefresh : Boolean = false

    var beingEmptied: Boolean = false
    var shouldBeEmpty: Boolean = false

    lateinit var onRefreshError: () -> Unit
    lateinit var onRefreshSuccess: () -> Unit

    lateinit var onAppendLoading: () -> Unit
    lateinit var onAppendError: () -> Unit
    lateinit var onAppendSuccess: () -> Unit

    var ownerUID : Int = -1

    var selectedItems : MutableSet<Int> = mutableSetOf()

    lateinit var pagingSource: (()-> PagingSource)
    lateinit var currentPagingSource : PagingSource

    var onChipsChanged: (() -> Unit)? = null

    val pagingData: Flow<PagingData<Any>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = pageSize/2)
        ) {
            currentPagingSource = pagingSource()
            currentPagingSource
        }.flow.cachedIn(viewModelScope)
}