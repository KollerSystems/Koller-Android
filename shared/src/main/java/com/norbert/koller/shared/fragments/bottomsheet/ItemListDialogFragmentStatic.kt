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

class ItemListDialogFragmentStatic(override var list : ArrayList<ListItem>, alreadyChecked : ArrayList<String>? = null) : ItemListDialogFragmentBase(alreadyChecked) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView(list)
        allLoaded()
    }

    override fun toggleList(): Boolean {
        return getValuesOnFinish != null
    }

}