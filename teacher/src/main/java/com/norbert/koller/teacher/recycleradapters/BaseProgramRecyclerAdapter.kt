package com.norbert.koller.teacher.recycleradapters

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.recycleradapters.BaseProgramRecyclerAdapter
import com.norbert.koller.teacher.fragments.BaseProgramFragment

class BaseProgramRecyclerAdapter : BaseProgramRecyclerAdapter() {
    override fun onItemPress(holder: RecyclerView.ViewHolder, item : BaseProgramData) {
        (holder.itemView.context as MainActivity).addFragmentWithTransition(BaseProgramFragment(item.id), holder.itemView, getTransitionName(item.getMainID()))
    }
}