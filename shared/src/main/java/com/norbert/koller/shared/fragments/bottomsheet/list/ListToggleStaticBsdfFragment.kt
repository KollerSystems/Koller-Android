package com.norbert.koller.shared.fragments.bottomsheet.list

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListToggleStaticRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentCardViewModel
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentPropertiesViewModel
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentToggleViewModel

class ListToggleStaticBsdfFragment() : ListCardBsdfFragment() {


    override fun getSetViewModel() : ListBsdfFragmentCardViewModel {
        return ViewModelProvider(this)[ListBsdfFragmentToggleViewModel::class.java]
    }

    fun getToggleViewModel() : ListBsdfFragmentToggleViewModel{
        return viewModel as ListBsdfFragmentToggleViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(getToggleViewModel().list.value.isNullOrEmpty()) return

        setRecyclerView(ListToggleStaticRecyclerAdapter(this))
        if(getToggleViewModel().list.value!!.size > 15){
            createSearchView()
        }
    }

    override fun onCancel(dialog: DialogInterface) {


        if(getRecyclerView().adapter != null) {

            getAdapter().filter("")

            val localizedStringList: ArrayList<String> = arrayListOf()

            for (item in getToggleViewModel().list.value!!) {
                item as ListToggleItem
                if(getToggleViewModel().selectedItems.contains(item.id)) {
                    localizedStringList.add(item.title)
                }
            }

            setFragmentResult("", Bundle(getToggleViewModel().selectedItems, localizedStringList))

        }

        getToggleViewModel().selectedItems = mutableSetOf()
        super.onCancel(dialog)
    }
}