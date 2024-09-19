package com.norbert.koller.teacher.recycleradapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.databinding.ItemBinding
import com.norbert.koller.shared.databinding.ItemReadableBinding
import com.norbert.koller.shared.databinding.ItemTextBinding
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBsdfFragment
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.teacher.activities.RoomPresenceActivity

class RoomPresenceRecyclerAdapter() : ApiRecyclerAdapter() {

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecyclerViewHelper.ItemViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {


        holder as RecyclerViewHelper.ItemViewHolder

        item as RoomData

        holder.itemBinding.textTitle.text = "Egy hiányzó"
        holder.itemBinding.textDescription.text = "Kis Gazsi"
        holder.itemBinding.imageIcon.setImageResource(R.drawable.priority)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemBinding.root.context, RoomPresenceActivity::class.java)
            holder.itemBinding.root.context.startActivity(intent)
            /*val dialog = RoomOrderBsdfFragment()
            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBsdfFragment.TAG)*/
        }
    }


}