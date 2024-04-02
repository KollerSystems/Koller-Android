package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norbert.koller.shared.recycleradapters.ListItem

abstract class ItemListDialogViewModel : ViewModel() {

    var list = MutableLiveData<ArrayList<ListItem>>()

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null

    var collapseText : Boolean = false
}