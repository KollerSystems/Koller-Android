package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norbert.koller.shared.recycleradapters.ListItem

abstract class ListBsdfFragmentViewModel : ViewModel() {

    var list = MutableLiveData<ArrayList<ListItem>?>()

    var filterName : Int? = null

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null

    var collapseText : Boolean = false
}