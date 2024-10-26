package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.ListItem
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.viewmodels.ListToggleBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListToggleStaticBsdfFragmentViewModel

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