package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.BaseProgramsFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter

import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.student.recycleradapters.ProgramRecyclerAdapter
import com.norbert.koller.shared.R as Rs

class BaseProgramListFragment : BaseProgramsFragment() {

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return ProgramRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()
        addDateChip()

        val lessonLocalName : String = requireContext().getString(Rs.string.lesson).lowercase()
        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))
    }
}