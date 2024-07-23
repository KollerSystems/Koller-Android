package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.FragmentBsdListBinding
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.viewmodels.ListBsdFragmentViewModel

abstract class ListBsdFragment(var alreadyChecked : ArrayList<String>? = null) : BottomSheetDialogFragment() {

    lateinit var binding : FragmentBsdListBinding

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null

    lateinit var viewModel: ListBsdFragmentViewModel

    lateinit var adapter : ListAdapter

    var collapseText : Boolean = false

    abstract fun toggleList() : Boolean


    fun setRecyclerView(){

        adapter = ListAdapter(this@ListBsdFragment)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBsdListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState == null){
            if(getValuesOnFinish != null) {
                viewModel.getValuesOnFinish = getValuesOnFinish
            }
            viewModel.collapseText = collapseText

        }
        else{
            dismiss()
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

            binding.ly.addView(frameLayout, 1)
            frameLayout.addView(searchView)

            searchView.getEditText().doOnTextChanged { text, start, before, count ->
                adapter.filter(text.toString())
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        Log.d("TEST", "1")

        if(viewModel.getValuesOnFinish != null) {

            adapter.filter("")

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