package com.norbert.koller.shared.recycleradapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ItemUserSmallBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper


class UserPreviewRecyclerAdapter (private var todayList : ArrayList<UserData>, var context : Context) : RecyclerView.Adapter<UserPreviewRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemUserSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = todayList[position]

        RecyclerViewHelper.roundRecyclerItemsHorizontallyGrid(holder.itemView, position, todayList.size)

        holder.itemBinding.user.setUser(currentItem)

        val nameParts : List<String> = currentItem.name!!.split(" ")
        val name = nameParts[0] + " " + nameParts [1]

        holder.itemBinding.textName.text = name.replace(" ", "\n")

        holder.itemView.setOnClickListener {

            if(currentItem.uid == UserData.instance.uid){
                ApplicationManager.openProfile.invoke(holder.itemView.context)
            }
            else{
                (context as MainActivity).addFragment(ApplicationManager.userFragment(currentItem.uid))
            }
        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class ViewHolder(val itemBinding: ItemUserSmallBinding) : RecyclerView.ViewHolder(itemBinding.root)
}