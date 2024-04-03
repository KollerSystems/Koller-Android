package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.ItemListDialogApiViewModel
import com.norbert.koller.shared.viewmodels.ItemListDialogStaticViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class ItemListDialogFragmentStatic(var list : ArrayList<ListItem>? = null, alreadyChecked : ArrayList<String>? = null) : ItemListDialogFragmentBase(alreadyChecked) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[ItemListDialogStaticViewModel::class.java]

        if(savedInstanceState == null){

            val listCopy : ArrayList<ListItem> = arrayListOf()
            for(listElement in list!!){
                listCopy.add(ListItem(listElement.title, listElement.description, listElement.icon, listElement.tag))
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