package com.norbert.koller.teacher.recycleradapters

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.recycleradapters.BaseProgramRecyclerAdapter
import com.norbert.koller.teacher.fragments.UserPresenceFragment

class BaseProgramRecyclerAdapter : BaseProgramRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item : BaseProgramData) {
        (fragmentActivity as MainActivity).addFragment(UserPresenceFragment(item.id))
    }
}