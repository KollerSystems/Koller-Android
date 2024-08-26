package com.norbert.koller.shared.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.fragments.HomeFragment
import com.norbert.koller.shared.managers.ApplicationManager

class MainActivityViewModel : ViewModel()  {

    var mainFragmentList : ArrayList<Int> = arrayListOf()

    var savedBackStacks : MutableSet<Int> = mutableSetOf()

    var lastFragmentId : String = "home"

    var descriptionHeight : Int = 0

    var currentBottomSheetDialogFragment : BottomSheetDialogFragment? = null

}