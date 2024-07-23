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
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.getAttributeColor

class SimpleUserRecyclerAdapter(var userList : List<UserData>) : RecyclerView.Adapter<SimpleUserRecyclerAdapter.ViewHolder>() {

    lateinit var recyclerView : RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)

        return ViewHolder(view)


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]

        val context = holder.itemView.context

        RecyclerViewHelper.marginItemsVertically(holder.itemView, position, userList.size)

        holder.userBadge.setUser(currentItem)
        holder.title.text = currentItem.name
        holder.description.text = currentItem.createDescription()


        if(position == 0){
            (holder.itemView as MaterialCardView).setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerHigh))
        }

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val userBadge : UserView = itemView.findViewById(R.id.user)
        val title : TextView = itemView.findViewById(R.id.text_title)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}