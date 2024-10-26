package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norbert.koller.shared.data.ListItem

open class ListBsdfFragmentViewModel : ViewModel() {
    var list = MutableLiveData<ArrayList<ListItem>?>()
    var title : String? = null

    var collapseText : Boolean = false
}