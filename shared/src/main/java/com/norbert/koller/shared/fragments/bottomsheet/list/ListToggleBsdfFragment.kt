package com.norbert.koller.shared.fragments.bottomsheet.list

import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor

abstract class ListToggleBsdfFragment() : ListBsdfFragment() {

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