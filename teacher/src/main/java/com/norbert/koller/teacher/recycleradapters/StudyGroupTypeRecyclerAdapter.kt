package com.norbert.koller.teacher.recycleradapters

import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.recycleradapters.StudyGroupTypeRecyclerAdapter
import com.norbert.koller.teacher.fragments.StudyGroupTypeFragment

class StudyGroupTypeRecyclerAdapter() : StudyGroupTypeRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item: StudyGroupTypeData) {
        (fragmentActivity as MainActivity).addFragment(StudyGroupTypeFragment(item.id))
    }


}