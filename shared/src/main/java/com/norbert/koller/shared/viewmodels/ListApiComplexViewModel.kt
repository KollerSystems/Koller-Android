package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import kotlinx.coroutines.flow.Flow

class ListApiComplexViewModel : ListViewModel() {

    var filtersShown : MutableLiveData<Boolean> = MutableLiveData(false)

    lateinit var sortOptions : ArrayList<String>

    var filters : MutableMap<String, MutableSet<Int>> = mutableMapOf()
    var dateFilters : MutableMap<String, androidx.core.util.Pair<Long, Long>> = mutableMapOf()



    var selectedSort : Int = 0
}