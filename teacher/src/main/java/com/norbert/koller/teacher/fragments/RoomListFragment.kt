package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListStaticBsdfFragment
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.activities.CreateOutgoingActivity
import com.norbert.koller.teacher.activities.CreatePlacesActivity
import com.norbert.koller.teacher.activities.CreatePlacesActivity.Companion.ROOM

class RoomListFragment() : com.norbert.koller.shared.fragments.RoomListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton("Helyiségek létrehozása"){
            val dialog = ListStaticBsdfFragment(arrayListOf(
                ListItem(
                    getString(com.norbert.koller.shared.R.string.room),
                    null,
                    AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.room),
                    null, {
                        val intent = Intent(requireContext(), CreatePlacesActivity::class.java)
                        intent.putExtra("type", CreatePlacesActivity.ROOM)
                        requireContext().startActivity(intent)
                    })

            ))
            dialog.show(parentFragmentManager, ListBsdfFragment.TAG)
        }
    }

}