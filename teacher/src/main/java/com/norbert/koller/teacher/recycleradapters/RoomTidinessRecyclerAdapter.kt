package com.norbert.koller.teacher.recycleradapters

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
import com.norbert.koller.teacher.activities.RoomRateActivity

class RoomTidinessRecyclerAdapter : RoomTidinessRecyclerAdapter() {
    override fun onClick(holder: RecyclerView.ViewHolder, item: BaseData) {
        val intent = Intent(holder.itemView.context, RoomRateActivity::class.java)
        holder.itemView.context.startActivity(intent)
    }
}