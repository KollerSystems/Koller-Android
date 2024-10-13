package com.norbert.koller.student.recycleradapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.databinding.ItemTextBinding
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBsdfFragment
import com.norbert.koller.shared.recycleradapters.RoomTidinessRecyclerAdapter

class RoomTidinessRecyclerAdapter() : RoomTidinessRecyclerAdapter() {
    override fun onClick(holder: RecyclerView.ViewHolder, item: BaseData) {
        val dialog = RoomOrderBsdfFragment()
        dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBsdfFragment.TAG)
    }


}