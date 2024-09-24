package com.norbert.koller.student.recycleradapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.recycleradapters.StudyGroupTypeRecyclerAdapter
import com.norbert.koller.student.fragments.StudyGroupTypeFragment

class StudyGroupTypeRecyclerAdapter() : StudyGroupTypeRecyclerAdapter() {

    override fun getFragment(item: StudyGroupTypeData): Fragment {
        val fragment = StudyGroupTypeFragment()
        val bundle = Bundle()
        bundle.putInt("id", item.getMainID())
        fragment.arguments = bundle
        return fragment
    }
}