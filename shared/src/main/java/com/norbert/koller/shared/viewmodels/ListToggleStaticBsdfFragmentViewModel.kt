package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import com.norbert.koller.shared.data.ListItem

class ListToggleStaticBsdfFragmentViewModel : ListToggleBsdfFragmentViewModel() {

    var selectedItems : MutableSet<Int> = mutableSetOf()
}