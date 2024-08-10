package com.norbert.koller.shared.fragments.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.R

abstract class RecyclerBsdFragment : BsdFragment() {
    override fun getContentHolder(inflater: LayoutInflater): ViewGroup {
        val recyclerView = RecyclerView(requireContext())
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, 0)
        layoutParams.weight = 1f
        val padding = requireContext().resources.getDimensionPixelSize(R.dimen.card_margin)
        recyclerView.setPadding(0,padding,0,padding)
        recyclerView.layoutParams = layoutParams
        return recyclerView
    }

    fun getRecyclerView() : RecyclerView {
        return viewGroup as RecyclerView
    }
}