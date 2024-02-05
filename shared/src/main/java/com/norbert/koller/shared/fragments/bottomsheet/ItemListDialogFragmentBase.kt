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
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.recycleradapters.ListItem

abstract class ItemListDialogFragmentBase(val alreadyChecked : ArrayList<String>? = null) : BottomSheetDialogFragment() {

    open lateinit var list : ArrayList<ListItem>

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedStrings : ArrayList<String>) -> Unit)? = null

    lateinit var recycleView : RecyclerView

    abstract fun toggleList() : Boolean


    fun setRecyclerView(listItemList : ArrayList<ListItem>){

        recycleView.adapter = ListAdapter(this@ItemListDialogFragmentBase, listItemList)
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

    override fun onCancel(dialog: DialogInterface) {
        Log.d("TEST", "1")

        if(getValuesOnFinish != null) {

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