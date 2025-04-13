package com.norbert.koller.shared.fragments.bottomsheet.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.bottomsheet.RecyclerBsdfFragment
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentCardViewModel
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentPropertiesViewModel

abstract class ListBsdfFragment() : RecyclerBsdfFragment() {

    lateinit var viewModel: ListBsdfFragmentPropertiesViewModel

    fun setRecyclerView(adapter : RecyclerView.Adapter<*>){
        getRecyclerView().adapter = adapter
        getRecyclerView().layoutManager = LinearLayoutManager(context)
        getRecyclerView().setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getSetViewModel()


        if(viewModel.title != null) {
            setTitle(viewModel.title!!)
        }
    }

    open fun getSetViewModel() : ListBsdfFragmentPropertiesViewModel{
        return ViewModelProvider(this)[ListBsdfFragmentPropertiesViewModel::class.java]
    }

    companion object {
        const val TAG = "ItemListDialogFragment"
    }

    fun createSearchView(){


        dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        val searchView = com.norbert.koller.shared.customviews.SearchView(requireContext())
        val frameLayout = FrameLayout(requireContext())
        val margin = requireContext().resources.getDimensionPixelSize(R.dimen.spacing)
        val mlp = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mlp.setMargins(margin,0,margin,margin)
        searchView.layoutParams = mlp
        searchView.getEditText().hint = requireContext().getString(R.string.search)

        frameLayout.setBackgroundColor(requireContext().getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer))

        getRoot().addView(frameLayout, 1)
        frameLayout.addView(searchView)

        setupSearch(searchView)

    }

    abstract fun setupSearch(searchView: com.norbert.koller.shared.customviews.SearchView)

}