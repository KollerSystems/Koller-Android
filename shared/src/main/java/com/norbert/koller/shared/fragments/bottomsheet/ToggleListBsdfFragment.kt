package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListToggleBsdfFragmentViewModel

abstract class ToggleListBsdfFragment() : ListBsdfFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.list.observe(this){
            setList()
        }
    }

    protected fun setup(alreadyChecked : ArrayList<String>? = null, title: String? = null, collapseText: Boolean = false)  : ListBsdfFragment{
        setup(title, collapseText)
        for (item in viewModel.list.value!!) {
            item.isChecked = alreadyChecked!!.contains(item.tag)
        }
        return this
    }

    fun setList(){

        if(viewModel.list.value == null) return;

        if(viewModel.list.value!!.size > 15){
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

            searchView.getEditText().doOnTextChanged { text, start, before, count ->
                adapter!!.filter(text.toString())
            }
        }
    }


}