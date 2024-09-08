package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.CacheManager

class UserRecyclerAdapter() : ApiRecyclerAdapterWithTransition() {

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecyclerViewHelper.UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        super.onBindItemViewHolder(holder, item, position)

        val context = holder.itemView.context
        holder as RecyclerViewHelper.UserViewHolder

        item as UserData
        holder.itemBinding.user.getImage().setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.person
            )
        )
        holder.itemBinding.user.setUser(item)
        holder.itemBinding.textTitle.text = item.name
        holder.itemBinding.textDescription.text = item.createDescription()

        holder.itemView.setOnClickListener {

            if (item.uid == CacheManager.userData.uid) {
                ApplicationManager.openProfile(context)
            } else {

                val fragment = ApplicationManager.userFragmentWithData(item)
                (context as MainActivity).addFragmentWithTransition(fragment, holder.itemView as MaterialCardView, getTransitionName(item.getMainID()))
            }

        }
    }
}