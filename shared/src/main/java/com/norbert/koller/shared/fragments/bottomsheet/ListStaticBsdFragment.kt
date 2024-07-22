package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.ListStaticBsdFragmentViewModel

class ListStaticBsdFragment(var list : ArrayList<ListItem>? = null, alreadyChecked : ArrayList<String>? = null) : ListBsdFragment(alreadyChecked) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[ListStaticBsdFragmentViewModel::class.java]

        if(savedInstanceState == null){

            val listCopy : ArrayList<ListItem> = arrayListOf()
            for(listElement in list!!){
                listCopy.add(ListItem(listElement.title, listElement.description, listElement.icon, listElement.tag, listElement.function))
            }
            viewModel.list.value = listCopy
        }

        super.onViewCreated(view, savedInstanceState)

        viewModel.list.observe(this){

            setRecyclerView()

        }
    }

    override fun toggleList(): Boolean {
        return viewModel.getValuesOnFinish != null
    }
}