package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class SearchViewModel : ViewModel() {


    var filters : MutableMap<String, ArrayList<String>> = mutableMapOf()
    var dateFilters : MutableMap<String, androidx.core.util.Pair<Long, Long>> = mutableMapOf()
    var onChipsChanged: (() -> Unit)? = null

    var selectedSort : Int = 0
    lateinit var sortOptions : ArrayList<String>
}