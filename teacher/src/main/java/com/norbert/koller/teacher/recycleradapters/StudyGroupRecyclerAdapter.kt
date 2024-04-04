package com.norbert.koller.teacher.recycleradapters

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter
import com.norbert.koller.teacher.activities.UserPresenceActivity

class StudyGroupRecyclerAdapter : StudyGroupRecyclerAdapter() {
    override fun onItemPress(fragmentActivity: FragmentActivity, item: StudyGroupData) {
        val intent = Intent(fragmentActivity as MainActivity, UserPresenceActivity::class.java)
        fragmentActivity.startActivity(intent)
    }
}