package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.StudyGroupListFragment
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.recycleradapters.StudyGroupRecyclerAdapter

class StudyGroupListFragment : StudyGroupListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerAdapter = StudyGroupRecyclerAdapter()


        addDateChip()

        val lessonLocalName : String = requireContext().getString(R.string.lesson).lowercase()

        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))


        super.onViewCreated(view, savedInstanceState)

    }

}