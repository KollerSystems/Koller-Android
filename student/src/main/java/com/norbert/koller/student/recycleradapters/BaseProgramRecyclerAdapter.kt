package com.norbert.koller.student.recycleradapters

import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.fragments.bottomsheet.BaseProgramDetailsFragment
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet
import com.norbert.koller.shared.recycleradapters.BaseProgramRecyclerAdapter

class BaseProgramRecyclerAdapter : BaseProgramRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item : BaseProgramData) {
        val dialog = BaseProgramDetailsFragment(item)
        dialog.show(fragmentActivity.supportFragmentManager, RoomOrderBottomSheet.TAG)
    }
}