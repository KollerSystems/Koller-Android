package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ProgramPagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class BaseProgramListFragment() : ListFragment() {

    override fun getFragmentTitle(): String? {
        return null
    }

    override fun getPagingSource(): PagingSource {
        return ProgramPagingSource(requireContext(), getBaseViewModel())
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")

        addDateChip()

        val lessonLocalName : String = requireContext().getString(R.string.lesson).lowercase()
        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))

        addSearchbar("Topic")
    }
}