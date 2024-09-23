package com.norbert.koller.teacher.recycleradapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter
import com.norbert.koller.teacher.fragments.StudyGroupSpecificFragment
import com.norbert.koller.teacher.fragments.StudyGroupTypeFragment

class StudyGroupRecyclerAdapter : StudyGroupRecyclerAdapter() {
    override fun onItemPress(holder: ViewHolder, item: StudyGroupData) {
        val fragment = StudyGroupSpecificFragment()
        val bundle = Bundle()
        bundle.putInt("id", item.getMainID())
        fragment.arguments = bundle
        (holder.itemView.context as MainActivity).addFragmentWithTransition(fragment, holder.itemView, getTransitionName(item.getMainID()))
    }
}