package com.norbert.koller.shared.recycleradapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customview.RoundedBadgeImageView
import com.norbert.koller.shared.customview.UserPreview
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.RecyclerViewHelper


class UserPreviewRecyclerAdapter (private var todayList : ArrayList<UserData>, var context : Context) : RecyclerView.Adapter<UserPreviewRecyclerAdapter.TodayViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_preview, parent, false)
        return TodayViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        RecyclerViewHelper.roundRecyclerItemsHorizontallyGrid(holder.itemView, position, todayList.size)

        holder.userBadge.setColorBasedOnClass(currentItem.Class?.Class)

        val nameParts : List<String> = currentItem.Name!!.split(" ")
        val name = nameParts[0] + " " + nameParts [1]

        holder.text.text = name.replace(" ", "\n")

        holder.itemView.setOnClickListener {

            if(currentItem.UID == UserData.instance.UID){
                MyApplication.openProfile.invoke(holder.itemView.context)
            }
            else{
                val bundle = Bundle()
                bundle.putInt("UID", currentItem.UID)
                val fragment = MyApplication.userFragment()
                fragment.arguments = bundle
                (context as MainActivity).addFragment(fragment)
            }

        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }


    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val userBadge : RoundedBadgeImageView = itemView.findViewById(R.id.badge_user)
        val text : TextView = itemView.findViewById(R.id.text_name)
    }


}