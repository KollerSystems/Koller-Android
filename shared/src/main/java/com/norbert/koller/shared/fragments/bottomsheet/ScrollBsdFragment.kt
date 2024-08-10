package com.norbert.koller.shared.fragments.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ScrollBsdFragment : BsdFragment() {
    override fun getContentHolder(inflater: LayoutInflater): ViewGroup {
        val scrollView = NestedScrollView(requireContext())
        scrollView.addView(getContent(inflater))
        return scrollView
    }

    abstract fun getContent(inflater: LayoutInflater) : ViewGroup

    fun getScrollView() : ScrollView{
        return viewGroup as ScrollView
    }
}