package com.norbert.koller.shared.recycleradapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ItemUserSmallBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.getAttributeColor


class UserPreviewRecyclerAdapter (private var userList : ArrayList<UserData>, var context : Context) : RecyclerView.Adapter<UserPreviewRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemUserSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        RecyclerViewHelper.roundRecyclerItemsHorizontallyGrid(holder.itemView, position, itemCount)

        var currentItem : UserData? = null

        for (i in 0 until userList.size){
            if(position == userList[i].bedNum){
                currentItem = userList[i]
                break
            }
        }

        if(currentItem != null){
            holder.itemBinding.ly.visibility = VISIBLE
            holder.itemBinding.root.setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerLow))
            holder.itemBinding.root.strokeWidth = 0

            holder.itemBinding.user.setUser(currentItem)

            val nameParts : List<String> = currentItem.name!!.split(" ")
            val name = nameParts[0] + " " + nameParts [1]

            holder.itemBinding.text.text = name.replace(" ", "\n")

            holder.itemView.setOnClickListener {

                if(currentItem.uid == CacheManager.userData.uid){
                    ApplicationManager.openProfile.invoke(holder.itemView.context)
                }
                else{
                    (context as MainActivity).addFragment(ApplicationManager.userFragment(currentItem.uid))
                }
            }
        }
        else{
            holder.itemBinding.ly.visibility = INVISIBLE
            holder.itemBinding.root.setCardBackgroundColor(Color.TRANSPARENT)
            holder.itemBinding.root.strokeWidth = ApplicationManager.convertDpToPixel(10, context)
        }
    }

    override fun getItemCount(): Int {
        if(userList.size<=4){
            return 4
        }

        if(userList.size % 2 != 0){
            return userList.size + 1
        }

        return userList.size
    }

    class ViewHolder(val itemBinding: ItemUserSmallBinding) : RecyclerView.ViewHolder(itemBinding.root)
}