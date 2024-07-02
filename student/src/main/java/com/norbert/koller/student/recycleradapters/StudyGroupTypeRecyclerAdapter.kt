package com.norbert.koller.student.recycleradapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.recycleradapters.StudyGroupTypeRecyclerAdapter
import com.norbert.koller.student.fragments.StudyGroupTypeFragment
import com.norbert.koller.student.fragments.bottomsheet.StudyGroupDetailsFragment

class StudyGroupTypeRecyclerAdapter() : StudyGroupTypeRecyclerAdapter() {

    override fun getFragment(item: StudyGroupTypeData): Fragment {
        return StudyGroupTypeFragment(item.getMainID())
    }
}