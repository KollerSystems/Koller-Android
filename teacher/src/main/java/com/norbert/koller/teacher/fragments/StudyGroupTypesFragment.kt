package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupTypesFragment
import com.norbert.koller.teacher.recycleradapters.StudyGroupTypeRecyclerAdapter

class StudyGroupTypesFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : StudyGroupTypesFragment(defaultFilters) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        apiRecyclerAdapter = StudyGroupTypeRecyclerAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

}