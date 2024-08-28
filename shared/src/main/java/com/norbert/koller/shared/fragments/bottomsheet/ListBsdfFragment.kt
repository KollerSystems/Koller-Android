package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel

abstract class ListBsdfFragment(private var alreadyChecked : ArrayList<String>? = null, private val filterName : Int? = null) : RecyclerBsdfFragment() {

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null

    lateinit var viewModel: ListBsdfFragmentViewModel

    var adapter : ListAdapter? = null

    var collapseText : Boolean = false

    abstract fun toggleList() : Boolean


    fun setRecyclerView(){

        adapter = ListAdapter(this@ListBsdfFragment)
        getRecyclerView().adapter = adapter
        getRecyclerView().layoutManager = LinearLayoutManager(context)
        getRecyclerView().setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState == null){
            if(getValuesOnFinish != null) {
                viewModel.getValuesOnFinish = getValuesOnFinish
            }
            viewModel.collapseText = collapseText
            viewModel.filterName = filterName

        }
        else{
            dismiss()
        }

        if(viewModel.filterName != null) {
            setTitle(getString(viewModel.filterName!!))
        }

        viewModel.list.observe(this){

            if (savedInstanceState == null && alreadyChecked != null) {
                for (item in viewModel.list.value!!) {
                    item.isChecked = alreadyChecked!!.contains(item.tag)
                }
            }

            setList()
        }


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

    override fun onCancel(dialog: DialogInterface) {
        Log.d("TEST", "1")

        if(viewModel.getValuesOnFinish != null && adapter != null) {

            adapter!!.filter("")

            val stringList: ArrayList<String> = arrayListOf()
            val localizedStringList: ArrayList<String> = arrayListOf()

            for (item in viewModel.list.value!!) {
                if (item.isChecked) {
                    stringList.add(item.tag!!)
                    localizedStringList.add(item.title)
                }
            }

            viewModel.getValuesOnFinish!!.invoke(stringList, localizedStringList)
        }
        super.onCancel(dialog)
    }


    companion object {
        const val TAG = "ItemListDialogFragment"
    }

}