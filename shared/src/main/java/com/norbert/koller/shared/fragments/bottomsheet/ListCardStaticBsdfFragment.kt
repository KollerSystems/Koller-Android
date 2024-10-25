package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ListCardRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.ListToggleRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel

class ListCardStaticBsdfFragment() : ListBsdfFragment() {
    override fun setViewModel(activity : AppCompatActivity): ListBsdfFragmentViewModel {
        return ViewModelProvider(activity)[ListBsdfFragmentViewModel::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.list.observe(this){

            setRecyclerView(ListCardRecyclerAdapter(this))

        }
    }

    fun setup(activity : AppCompatActivity, list : ArrayList<ListItem>? = null, title: String? = null, collapseText: Boolean = false)  : ListBsdfFragment{
        setup(activity, title, collapseText)
        viewModel.list.value = list
        return this
    }

}