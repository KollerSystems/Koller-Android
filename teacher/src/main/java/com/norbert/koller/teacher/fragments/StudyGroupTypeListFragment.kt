package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupTypeListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.teacher.recycleradapters.StudyGroupTypeRecyclerAdapter

class StudyGroupTypeListFragment() : StudyGroupTypeListFragment() {
    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return StudyGroupTypeRecyclerAdapter()
    }

}