package com.norbert.koller.shared.viewmodels

open class ListToggleBsdfFragmentViewModel : ListBsdfFragmentViewModel() {

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null


}