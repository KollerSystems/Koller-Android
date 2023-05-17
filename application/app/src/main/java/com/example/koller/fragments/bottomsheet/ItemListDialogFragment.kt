package com.example.koller.fragments.bottomsheet

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koller.R

class ItemListDialogFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var recycleView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)

        recycleView = view.findViewById(R.id.simple_list_bottom_fragment_recycle_view)

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.setHasFixedSize(true)

        return view
    }



    companion object {
        const val TAG = "ItemListDialogFragment"
    }

}