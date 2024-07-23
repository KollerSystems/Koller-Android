package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupsFragment
import com.norbert.koller.teacher.recycleradapters.StudyGroupTypeRecyclerAdapter

class StudyGroupTypesFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : StudyGroupsFragment(defaultFilters) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        apiRecyclerAdapter = StudyGroupTypeRecyclerAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

}