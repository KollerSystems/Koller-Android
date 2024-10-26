package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.data.ListItem
import com.norbert.koller.shared.recycleradapters.ListCardRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListCardStaticBsdfFragmentViewModel

class ListCardStaticBsdfFragment() : ListBsdfFragment() {
    override fun setViewModel(activity : AppCompatActivity): ListBsdfFragmentViewModel {
        return ViewModelProvider(activity)[ListCardStaticBsdfFragmentViewModel::class.java]
    }

    fun getListCardStaticViewModel() : ListCardStaticBsdfFragmentViewModel{
        return viewModel as ListCardStaticBsdfFragmentViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListCardStaticViewModel().list.observe(this){

            setRecyclerView(ListCardRecyclerAdapter(this))

        }
    }

    fun setup(activity : AppCompatActivity, list : ArrayList<ListItem>? = null, title: String? = null, collapseText: Boolean = false)  : ListBsdfFragment{
        setup(activity, title, collapseText)
        getListCardStaticViewModel().list.value = list
        return this
    }

}