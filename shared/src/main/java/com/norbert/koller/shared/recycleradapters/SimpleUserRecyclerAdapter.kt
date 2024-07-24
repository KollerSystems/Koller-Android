package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.getAttributeColor

class SimpleUserRecyclerAdapter(var userList : List<UserData>) : RecyclerView.Adapter<RecyclerViewHelper.UserViewHolder>() {

    lateinit var recyclerView : RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHelper.UserViewHolder {
        return RecyclerViewHelper.UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerViewHelper.UserViewHolder, position: Int) {
        val currentItem = userList[position]

        val context = holder.itemView.context

        RecyclerViewHelper.marginItemsVertically(holder.itemView, position, userList.size)

        holder.itemBinding.user.setUser(currentItem)
        holder.itemBinding.textTitle.text = currentItem.name
        holder.itemBinding.textDescription.text = currentItem.createDescription()


        if(position == 0){
            (holder.itemView as MaterialCardView).setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerHigh))
        }

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}