package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupTypeListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.student.recycleradapters.StudyGroupTypeRecyclerAdapter

class StudyGroupTypeListFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : StudyGroupTypeListFragment(defaultFilters) {

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return StudyGroupTypeRecyclerAdapter()
    }

}