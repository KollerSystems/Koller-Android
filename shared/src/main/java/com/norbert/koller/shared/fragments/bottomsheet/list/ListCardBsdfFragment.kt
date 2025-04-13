package com.norbert.koller.shared.fragments.bottomsheet.list

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ListCardRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentCardViewModel
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentPropertiesViewModel
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentToggleViewModel

abstract class ListCardBsdfFragment() : ListBsdfFragment() {


    override fun getSetViewModel() : ListBsdfFragmentCardViewModel{
        return ViewModelProvider(this)[ListBsdfFragmentCardViewModel::class.java]
    }

    fun getCardViewModel() : ListBsdfFragmentCardViewModel{
        return viewModel as ListBsdfFragmentCardViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCardViewModel().list.observe(this){
            setRecyclerView(ListCardRecyclerAdapter(this))
        }
    }

    fun getAdapter() : ListRecyclerAdapter{
        return getRecyclerView().adapter as ListRecyclerAdapter
    }

    override fun setupSearch(searchView: SearchView) {
        searchView.getEditText().doOnTextChanged { text, start, before, count ->
            getAdapter().filter(text.toString())
        }
    }
}