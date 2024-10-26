package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectBaseProgram
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator

abstract class BaseProgramListFragment() : ListFragment() {

    override fun getFragmentTitle(): String? {
        return null
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectBaseProgram()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")

        addDateChip()

        val lessonLocalName : String = requireContext().getString(R.string.lesson).lowercase()
        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListToggleItem("1 ${lessonLocalName}", null, null, 1),
            ListToggleItem("2 ${lessonLocalName}", null, null, 2)
        ))

        addSearchbar("Topic")
    }
}