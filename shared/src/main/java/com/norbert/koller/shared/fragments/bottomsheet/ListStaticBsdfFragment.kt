package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListStaticToggleBsdfFragmentViewModel

class ListStaticBsdfFragment() : ToggleListBsdfFragment() {

    public fun setup(list : ArrayList<ListItem>? = null, alreadyChecked : ArrayList<String>? = null, title: String? = null, collapseText: Boolean = false) : ListBsdfFragment{
        setup(alreadyChecked, title, collapseText)
        viewModel.list.value = list
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(savedInstanceState == null){

            val listCopy : ArrayList<ListItem> = arrayListOf()
            for(listElement in viewModel.list.value!!){
                listCopy.add(ListItem(listElement.title, listElement.description, listElement.icon, listElement.tag, listElement.function))
            }
            viewModel.list.value = listCopy
        }

        super.onViewCreated(view, savedInstanceState)

        viewModel.list.observe(this){

            setRecyclerView()

        }
    }



    override fun setViewModel(): ListBsdfFragmentViewModel {
        return ViewModelProvider(this)[ListStaticToggleBsdfFragmentViewModel::class.java]
    }
}