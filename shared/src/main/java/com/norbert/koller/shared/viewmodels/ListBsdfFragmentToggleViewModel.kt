package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import com.norbert.koller.shared.data.ListItem

class ListBsdfFragmentToggleViewModel : ListBsdfFragmentCardViewModel() {

    var selectedItems : MutableSet<Int> = mutableSetOf()
}