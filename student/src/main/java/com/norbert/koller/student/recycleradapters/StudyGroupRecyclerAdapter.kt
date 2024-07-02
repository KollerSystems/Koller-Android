package com.norbert.koller.student.recycleradapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.student.fragments.bottomsheet.BaseProgramDetailsFragment
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter
import com.norbert.koller.student.fragments.StudyGroupTypeFragment
import com.norbert.koller.student.fragments.bottomsheet.StudyGroupDetailsFragment

class StudyGroupRecyclerAdapter : StudyGroupRecyclerAdapter() {

    override fun onItemPress(holder: ViewHolder, item: StudyGroupData) {
        val dialog = StudyGroupDetailsFragment(item)
        dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBottomSheet.TAG)
    }
}