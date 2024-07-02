package com.norbert.koller.teacher.recycleradapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.recycleradapters.StudyGroupTypeRecyclerAdapter
import com.norbert.koller.teacher.fragments.StudyGroupSpecificFragment
import com.norbert.koller.teacher.fragments.StudyGroupTypeFragment

class StudyGroupTypeRecyclerAdapter() : StudyGroupTypeRecyclerAdapter() {
    override fun getFragment(item: StudyGroupTypeData): Fragment {
        return StudyGroupTypeFragment(item.id)
    }


}