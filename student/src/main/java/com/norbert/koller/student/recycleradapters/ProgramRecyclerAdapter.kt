package com.norbert.koller.student.recycleradapters

import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.student.fragments.bottomsheet.BaseProgramDetailsBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBsdfFragment
import com.norbert.koller.shared.recycleradapters.ProgramRecyclerAdapter

class ProgramRecyclerAdapter : ProgramRecyclerAdapter() {
    override fun onItemPress(holder: RecyclerView.ViewHolder, item : BaseProgramData) {
        val dialog = BaseProgramDetailsBsdfFragment(item)
        dialog.show((holder.itemView.context as MainActivity).supportFragmentManager, RoomOrderBsdfFragment.TAG)
    }
}