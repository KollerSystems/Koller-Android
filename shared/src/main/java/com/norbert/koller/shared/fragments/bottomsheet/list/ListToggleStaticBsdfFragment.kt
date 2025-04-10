package com.norbert.koller.shared.fragments.bottomsheet.list

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.data.ListItem
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListToggleStaticRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListToggleStaticBsdfFragmentViewModel

class ListToggleStaticBsdfFragment() : ListToggleBsdfFragment() {

    fun getAdapter() : ListRecyclerAdapter{
        return getRecyclerView().adapter as ListRecyclerAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(getToggleViewModel().list.value.isNullOrEmpty()) return

        setRecyclerView(ListToggleStaticRecyclerAdapter(this))
        if(getToggleViewModel().list.value!!.size > 15){
            createSearchView()
        }
    }

    override fun setViewModel(activity : AppCompatActivity): ListBsdfFragmentViewModel {
        return ViewModelProvider(activity)[ListToggleStaticBsdfFragmentViewModel::class.java]
    }

    fun getToggleViewModel() : ListToggleStaticBsdfFragmentViewModel{
        return viewModel as ListToggleStaticBsdfFragmentViewModel
    }

    override fun onCancel(dialog: DialogInterface) {

        Log.d("TEST", getValuesOnFinish.toString())

        if(getValuesOnFinish != null && getRecyclerView().adapter != null) {

            getAdapter().filter("")

            val localizedStringList: ArrayList<String> = arrayListOf()

            for (item in getToggleViewModel().list.value!!) {
                item as ListToggleItem
                if(getToggleViewModel().selectedItems.contains(item.id)) {
                    localizedStringList.add(item.title)
                }
            }

            getValuesOnFinish!!.invoke(getToggleViewModel().selectedItems, localizedStringList)
        }

        getToggleViewModel().selectedItems = mutableSetOf()
        super.onCancel(dialog)
    }

    override fun setupSearch(searchView: SearchView) {
        searchView.getEditText().doOnTextChanged { text, start, before, count ->
            getAdapter().filter(text.toString())
        }
    }
}