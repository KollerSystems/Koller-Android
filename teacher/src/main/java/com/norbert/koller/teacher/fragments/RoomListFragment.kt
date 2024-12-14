package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListCardStaticBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListToggleStaticBsdfFragment
import com.norbert.koller.teacher.activities.CreatePlacesActivity

class RoomListFragment() : com.norbert.koller.shared.fragments.RoomListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton("Helyiségek létrehozása"){
            val dialog = ListCardStaticBsdfFragment().setup((context as AppCompatActivity), arrayListOf(
                ListCardItem(
                    getString(R.string.rooms),
                    null,
                    AppCompatResources.getDrawable(requireContext(), R.drawable.room), {
                        val intent = Intent(requireContext(), CreatePlacesActivity::class.java)
                        intent.putExtra("type", CreatePlacesActivity.ROOM)
                        requireContext().startActivity(intent)
                    })

            ))
            dialog.show(parentFragmentManager, ListBsdfFragment.TAG)
        }
    }

}