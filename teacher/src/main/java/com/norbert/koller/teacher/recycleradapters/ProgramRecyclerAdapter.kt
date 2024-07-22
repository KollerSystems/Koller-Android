package com.norbert.koller.teacher.recycleradapters

import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.recycleradapters.ProgramRecyclerAdapter
import com.norbert.koller.teacher.fragments.BaseProgramFragment

class ProgramRecyclerAdapter : ProgramRecyclerAdapter() {
    override fun onItemPress(holder: RecyclerView.ViewHolder, item : BaseProgramData) {
        (holder.itemView.context as MainActivity).addFragmentWithTransition(BaseProgramFragment(item.id), holder.itemView, getTransitionName(item.getMainID()))
    }
}