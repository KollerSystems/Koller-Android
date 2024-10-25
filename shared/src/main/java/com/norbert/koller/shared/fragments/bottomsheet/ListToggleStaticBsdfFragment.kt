package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListToggleApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListToggleItem
import com.norbert.koller.shared.recycleradapters.ListToggleRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListStaticToggleBsdfFragmentViewModel

class ListToggleStaticBsdfFragment() : ListToggleBsdfFragment() {

    fun setup(activity : AppCompatActivity, list : ArrayList<ListItem>? = null, alreadyChecked : ArrayList<String>? = null, title: String? = null, collapseText: Boolean = false) : ListBsdfFragment{
        setup(activity, title, collapseText)
        val listCopy : ArrayList<ListItem> = arrayListOf()
        for(listElement in list!!){
            listElement as ListToggleItem
            listCopy.add(ListToggleItem(listElement.title, listElement.description, listElement.icon, listElement.tag, listElement.isChecked) as ListItem)
        }
        viewModel.list.value = listCopy
        mergeListWithCheckedElements(alreadyChecked)
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        viewModel.list.observe(this){

            setRecyclerView(ListToggleRecyclerAdapter(this))

        }
    }



    override fun setViewModel(activity : AppCompatActivity): ListBsdfFragmentViewModel {
        return ViewModelProvider(activity)[ListStaticToggleBsdfFragmentViewModel::class.java]
    }
}