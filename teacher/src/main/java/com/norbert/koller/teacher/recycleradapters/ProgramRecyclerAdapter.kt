package com.norbert.koller.teacher.recycleradapters

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.recycleradapters.ProgramRecyclerAdapter
import com.norbert.koller.teacher.fragments.BaseProgramFragment

class ProgramRecyclerAdapter : ProgramRecyclerAdapter() {
    override fun onItemPress(holder: RecyclerView.ViewHolder, item : BaseProgramData) {
        val fragment = BaseProgramFragment()
        val bundle = Bundle()
        bundle.putInt("id", item.getMainID())
        fragment.arguments = bundle
        (holder.itemView.context as MainActivity).addFragmentWithTransition(fragment, holder.itemView, getTransitionName(item.getMainID()))

    }
}