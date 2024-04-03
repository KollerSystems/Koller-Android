package com.norbert.koller.teacher.recycleradapters

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.fragments.bottomsheet.BaseProgramDetailsFragment
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet
import com.norbert.koller.shared.recycleradapters.BaseProgramRecyclerAdapter
import com.norbert.koller.teacher.activities.EditUserActivity
import com.norbert.koller.teacher.activities.UserPresenceActivity

class BaseProgramRecyclerAdapter : BaseProgramRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item : BaseProgramData) {
        val intent = Intent(fragmentActivity as MainActivity, UserPresenceActivity::class.java)
        fragmentActivity.startActivity(intent)
    }
}