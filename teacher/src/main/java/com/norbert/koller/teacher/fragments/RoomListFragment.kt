package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.fragments.ButtonParameters
import com.norbert.koller.shared.fragments.bottomsheet.list.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.list.ListCardStaticBsdfFragment
import com.norbert.koller.teacher.activities.CreatePlacesActivity
import com.norbert.koller.teacher.activities.RoomPresenceActivity
import com.norbert.koller.teacher.activities.RoomRateActivity

class RoomListFragment() : com.norbert.koller.shared.fragments.RoomListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButtons(ButtonParameters(getString(R.string.create_places), onClick = {
            val dialog = ListCardStaticBsdfFragment().setup((context as AppCompatActivity), arrayListOf(
                ListCardItem(
                    getString(Rs.string.rooms),
                    null,
                    AppCompatResources.getDrawable(requireContext(), Rs.drawable.room)
                ) {
                    val intent = Intent(requireContext(), CreatePlacesActivity::class.java)
                    intent.putExtra("type", CreatePlacesActivity.ROOM)
                    requireContext().startActivity(intent)
                }

            ))
            dialog.show(parentFragmentManager, ListBsdfFragment.TAG)
        }),ButtonParameters(getString(Rs.string.rate_rooms), Rs.drawable.clean, {
            val intent = Intent(requireContext(), RoomRateActivity::class.java)
            startActivity(intent)

        }),ButtonParameters(getString(Rs.string.staffing_control), Rs.drawable.presence, {
            val intent = Intent(requireContext(), RoomPresenceActivity::class.java)
            startActivity(intent)
        }))
    }

}