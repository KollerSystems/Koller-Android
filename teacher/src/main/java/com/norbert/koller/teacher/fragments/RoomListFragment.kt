package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListCardStaticBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListToggleStaticBsdfFragment
import com.norbert.koller.teacher.activities.CreatePlacesActivity

class RoomListFragment() : com.norbert.koller.shared.fragments.RoomListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton(getString(R.string.create_places)){
            val dialog = ListCardStaticBsdfFragment().setup((context as AppCompatActivity), arrayListOf(
                ListCardItem(
                    getString(Rs.string.rooms),
                    null,
                    AppCompatResources.getDrawable(requireContext(), Rs.drawable.room), {
                        val intent = Intent(requireContext(), CreatePlacesActivity::class.java)
                        intent.putExtra("type", CreatePlacesActivity.ROOM)
                        requireContext().startActivity(intent)
                    })

            ))
            dialog.show(parentFragmentManager, ListBsdfFragment.TAG)
        }

        addButton(getString(Rs.string.rate_rooms)){
            val dialog = ListCardStaticBsdfFragment().setup((context as AppCompatActivity), arrayListOf(
                ListCardItem(
                    getString(Rs.string.rooms),
                    null,
                    AppCompatResources.getDrawable(requireContext(), Rs.drawable.room), {
                        val intent = Intent(requireContext(), CreatePlacesActivity::class.java)
                        intent.putExtra("type", CreatePlacesActivity.ROOM)
                        requireContext().startActivity(intent)
                    })

            ))
            dialog.show(parentFragmentManager, ListBsdfFragment.TAG)
        }
    }

}