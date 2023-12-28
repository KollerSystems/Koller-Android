package com.norbert.koller.shared.recycleradapter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserFragment
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customview.RoundedBadgeImageView

class UserRecyclerAdapter(chipGroupSort: ChipGroup? = null, chipGroupFilter: ChipGroup? = null) : BaseRecycleAdapter(chipGroupSort, chipGroupFilter) {
    override fun getViewType(): Int {
        return R.layout.view_user_item
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


        val context = holder.itemView.context
        holder as UserViewHolder

        item as UserData
        holder.userBadge.image.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.person
            )
        )
        holder.userBadge.setColorBasedOnClass(item.Class?.Class)
        holder.title.text = item.Name
        holder.description.text = item.createDescription()

        holder.itemView.setOnClickListener {

            if (item.UID == UserData.instance.UID) {
                MyApplication.openProfile(context)
            } else {
                val bundle = Bundle()
                val fragment = MyApplication.userFragment(item.UID)
                fragment.arguments = bundle
                (context as MainActivity).addFragment(fragment)
            }

        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val userBadge : RoundedBadgeImageView = itemView.findViewById(R.id.user_badge)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}