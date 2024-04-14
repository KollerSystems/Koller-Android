package com.norbert.koller.teacher.recycleradapters

import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.recycleradapters.BaseProgramRecyclerAdapter
import com.norbert.koller.teacher.fragments.BaseProgramFragment

class BaseProgramRecyclerAdapter : BaseProgramRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item : BaseProgramData) {
        (fragmentActivity as MainActivity).addFragment(BaseProgramFragment(item.id))
    }
}