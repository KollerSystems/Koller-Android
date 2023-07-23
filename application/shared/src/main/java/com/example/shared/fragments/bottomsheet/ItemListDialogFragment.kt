package com.example.shared.fragments.bottomsheet

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shared.R
import com.example.shared.recycleradapter.ListAdapter
import com.example.shared.recycleradapter.ListItem

class ItemListDialogFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var recycleView : RecyclerView

    lateinit var list : ArrayList<ListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)

        recycleView = view.findViewById(R.id.simple_list_bottom_fragment_recycle_view)


        recycleView.adapter = ListAdapter(this, list)


        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.setHasFixedSize(true)

        return view
    }



    companion object {
        const val TAG = "ItemListDialogFragment"
    }

}