package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.StudyGroupListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.recycleradapters.StudyGroupRecyclerAdapter

class StudyGroupListFragment : StudyGroupListFragment() {
    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return StudyGroupRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        addDateChip()

        val lessonLocalName : String = requireContext().getString(R.string.lesson).lowercase()

        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))

    }

}