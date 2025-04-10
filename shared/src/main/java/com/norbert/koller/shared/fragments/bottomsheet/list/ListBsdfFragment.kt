package com.norbert.koller.shared.fragments.bottomsheet.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.fragments.bottomsheet.RecyclerBsdfFragment
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel

abstract class ListBsdfFragment() : RecyclerBsdfFragment() {

    abstract fun setViewModel(activity : AppCompatActivity) : ListBsdfFragmentViewModel

    var getValuesOnFinish: ((listOftTrue : MutableSet<Int>, localizedStrings : ArrayList<String>) -> Unit)? = null

    lateinit var viewModel: ListBsdfFragmentViewModel

    fun setRecyclerView(adapter : RecyclerView.Adapter<*>){
        getRecyclerView().adapter = adapter
        getRecyclerView().layoutManager = LinearLayoutManager(context)
        getRecyclerView().setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = setViewModel(requireContext() as AppCompatActivity)

        if(viewModel.title != null) {
            setTitle(viewModel.title!!)
        }
    }

    companion object {
        const val TAG = "ItemListDialogFragment"
    }

}