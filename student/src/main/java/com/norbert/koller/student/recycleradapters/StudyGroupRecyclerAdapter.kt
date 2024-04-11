package com.norbert.koller.student.recycleradapters

import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.student.fragments.bottomsheet.BaseProgramDetailsFragment
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter

class StudyGroupRecyclerAdapter : StudyGroupRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item: StudyGroupData) {
        val dialog = BaseProgramDetailsFragment(item)
        dialog.show(fragmentActivity.supportFragmentManager, RoomOrderBottomSheet.TAG)
    }
}