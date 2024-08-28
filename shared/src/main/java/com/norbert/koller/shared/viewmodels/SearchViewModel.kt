package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.ViewModel

open class SearchViewModel : ViewModel() {


    var filters : MutableMap<String, ArrayList<String>> = mutableMapOf()
    var dateFilters : MutableMap<String, androidx.core.util.Pair<Long, Long>> = mutableMapOf()
}