package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListToggleBsdfFragmentViewModel

abstract class ListBsdfFragment() : RecyclerBsdfFragment() {

    abstract fun setViewModel(activity : AppCompatActivity) : ListBsdfFragmentViewModel

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null

    lateinit var viewModel: ListBsdfFragmentViewModel


    protected fun setup(activity : AppCompatActivity, title : String? = null, collapseText : Boolean = false) : ListBsdfFragment{
        viewModel = setViewModel(activity)
        viewModel.title = title
        viewModel.collapseText = collapseText
        return this
    }

    fun setRecyclerView(adapter : RecyclerView.Adapter<*>){
        getRecyclerView().adapter = adapter
        getRecyclerView().layoutManager = LinearLayoutManager(context)
        getRecyclerView().setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState != null){
            dismiss()
        }

        if(viewModel.title != null) {
            setTitle(viewModel.title!!)
        }
    }

    companion object {
        const val TAG = "ItemListDialogFragment"
    }

}