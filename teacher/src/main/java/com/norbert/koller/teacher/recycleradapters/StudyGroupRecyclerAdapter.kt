package com.norbert.koller.teacher.recycleradapters

import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter
import com.norbert.koller.teacher.fragments.StudyGroupSpecificFragment

class StudyGroupRecyclerAdapter : StudyGroupRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item: StudyGroupData) {
        (fragmentActivity as MainActivity).addFragment(StudyGroupSpecificFragment(item.id))

    }
}