package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import okhttp3.internal.notifyAll

abstract class ItemListDialogFragmentBase(val alreadyChecked : ArrayList<String>? = null) : BottomSheetDialogFragment() {

    open lateinit var list : ArrayList<ListItem>

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null

    lateinit var recycleView : RecyclerView
    lateinit var adapter : ListAdapter

    var collapseText : Boolean = false

    abstract fun toggleList() : Boolean


    fun setRecyclerView(listItemList : ArrayList<ListItem>){

        adapter = ListAdapter(this@ItemListDialogFragmentBase, listItemList)
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bshd_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.simple_list_bottom_fragment_recycle_view)
    }

    fun allLoaded(){
        if(list.size > 15){
            dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


            val searchView = com.norbert.koller.shared.customviews.SearchView(requireContext())
            val margin = requireContext().resources.getDimensionPixelSize(R.dimen.spacing)
            val mlp = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            mlp.setMargins(margin,0,margin,margin)
            searchView.layoutParams = mlp

            val mainLayout : LinearLayout = requireView().findViewById(R.id.ly)

            mainLayout.addView(searchView, 1)

            searchView.editTextSearch.doOnTextChanged { text, start, before, count ->
                adapter.filter(text.toString())
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        Log.d("TEST", "1")

        if(getValuesOnFinish != null) {

            adapter.filter("")

            val stringList: ArrayList<String> = arrayListOf()
            val localizedStringList: ArrayList<String> = arrayListOf()

            for (item in list) {
                if (item.isChecked) {
                    stringList.add(item.tag!!)
                    localizedStringList.add(item.title)
                }
            }

            getValuesOnFinish!!.invoke(stringList, localizedStringList)
        }
        super.onCancel(dialog)
    }


    companion object {
        const val TAG = "ItemListDialogFragment"
    }

}