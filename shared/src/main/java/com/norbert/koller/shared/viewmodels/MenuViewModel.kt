package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapters.PagingSource
import kotlinx.coroutines.flow.Flow

open class MenuViewModel : ViewModel() {


    var filters : MutableMap<String, ArrayList<String>> = mutableMapOf()
    var dateFilters : MutableMap<String, androidx.core.util.Pair<Long, Long>> = mutableMapOf()
}