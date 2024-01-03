package com.norbert.koller.shared.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norbert.koller.shared.data.BaseData

class ResponseViewModel : ViewModel() {

    var id: Int? = null
    var response = MutableLiveData<Any>()
}