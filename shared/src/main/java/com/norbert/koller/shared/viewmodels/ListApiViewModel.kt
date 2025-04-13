package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData

class ListApiViewModel : ListViewModel() {

    var filtersShown : MutableLiveData<Boolean> = MutableLiveData(false)

    lateinit var sortOptions : ArrayList<String>

    var filters : MutableMap<String, MutableSet<Int>> = mutableMapOf()
    var dateFilters : MutableMap<String, androidx.core.util.Pair<Long, Long>> = mutableMapOf()



    var selectedSort : Int = 0
}