package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupsFragment
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.student.recycleradapters.StudyGroupTypeRecyclerAdapter

class StudyGroupsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : StudyGroupsFragment(defaultFilters) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        baseRecycleAdapter = StudyGroupTypeRecyclerAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

}