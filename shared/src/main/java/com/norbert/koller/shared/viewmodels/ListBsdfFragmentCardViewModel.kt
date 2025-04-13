package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import com.norbert.koller.shared.data.ListItem

open class ListBsdfFragmentCardViewModel : ListBsdfFragmentPropertiesViewModel() {

    var list = MutableLiveData<ArrayList<ListItem>?>()

}