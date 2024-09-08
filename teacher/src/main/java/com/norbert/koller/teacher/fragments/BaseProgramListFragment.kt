package com.norbert.koller.teacher.fragments

import com.norbert.koller.shared.fragments.BaseProgramListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.recycleradapters.ProgramRecyclerAdapter

class BaseProgramListFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : BaseProgramListFragment(defaultFilters) {

    override fun onSetUpSearching() {
        super.onSetUpSearching()


        addDateChip()

        val lessonLocalName : String = requireContext().getString(com.norbert.koller.shared.R.string.lesson).lowercase()

        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return ProgramRecyclerAdapter()
    }
}