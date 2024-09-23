package com.norbert.koller.teacher.fragments

import com.norbert.koller.shared.fragments.BaseProgramListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.recycleradapters.ProgramRecyclerAdapter

class BaseProgramListFragment() : BaseProgramListFragment() {

    override fun onSetUpSearching() {
        super.onSetUpSearching()
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return ProgramRecyclerAdapter()
    }
}