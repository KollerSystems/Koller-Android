package com.norbert.koller.student.recycleradapters

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBsdFragment
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter
import com.norbert.koller.student.fragments.bottomsheet.StudyGroupDetailsFragment

class StudyGroupRecyclerAdapter : StudyGroupRecyclerAdapter() {

    override fun onItemPress(holder: ViewHolder, item: StudyGroupData) {
        val dialog = StudyGroupDetailsFragment(item)
        dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBsdFragment.TAG)
    }
}