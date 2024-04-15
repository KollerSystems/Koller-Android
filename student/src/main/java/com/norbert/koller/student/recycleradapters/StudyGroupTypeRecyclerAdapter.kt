package com.norbert.koller.student.recycleradapters

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.recycleradapters.StudyGroupTypeRecyclerAdapter
import com.norbert.koller.student.fragments.StudyGroupTypeFragment

class StudyGroupTypeRecyclerAdapter() : StudyGroupTypeRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item: StudyGroupTypeData) {
        (fragmentActivity as MainActivity).addFragment(StudyGroupTypeFragment(item.id))
    }


}