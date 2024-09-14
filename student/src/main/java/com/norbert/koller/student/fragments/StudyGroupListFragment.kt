package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.StudyGroupListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.student.recycleradapters.StudyGroupRecyclerAdapter

class StudyGroupListFragment : StudyGroupListFragment() {

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return StudyGroupRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()
    }

}