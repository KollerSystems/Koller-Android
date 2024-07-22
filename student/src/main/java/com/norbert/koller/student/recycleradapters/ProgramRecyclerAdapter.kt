package com.norbert.koller.student.recycleradapters

import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.student.fragments.bottomsheet.BaseProgramDetailsFragment
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBsdFragment
import com.norbert.koller.shared.recycleradapters.ProgramRecyclerAdapter

class ProgramRecyclerAdapter : ProgramRecyclerAdapter() {
    override fun onItemPress(holder: RecyclerView.ViewHolder, item : BaseProgramData) {
        val dialog = BaseProgramDetailsFragment(item)
        dialog.show((holder.itemView.context as MainActivity).supportFragmentManager, RoomOrderBsdFragment.TAG)
    }
}