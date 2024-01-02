package com.norbert.koller.shared.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.norbert.koller.shared.fragments.HomeFragment
import com.norbert.koller.shared.managers.MyApplication

class MainActivityViewModel : ViewModel()  {

    var mainFragmentList : ArrayList<Int> = arrayListOf()

    var fragments : MutableMap<Int, Fragment> = mutableMapOf()

}