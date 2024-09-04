package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupTypeListFragment
import com.norbert.koller.teacher.recycleradapters.StudyGroupTypeRecyclerAdapter

class StudyGroupTypeListFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : StudyGroupTypeListFragment(defaultFilters) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerAdapter = StudyGroupTypeRecyclerAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

}