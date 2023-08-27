package com.norbert.koller.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserFragment
import com.google.android.material.card.MaterialCardView


class UserPreviewRecyclerAdapter (private var todayList : ArrayList<UserData>, var context : Context) : RecyclerView.Adapter<UserPreviewRecyclerAdapter.TodayViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_preview, parent, false)
        return TodayViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        MyApplication.roundRecyclerItemsHorizontally(context, holder.itemView, position, todayList.size)

        val nameParts : List<String> = currentItem.Name!!.split(" ")
        val name = nameParts[0] + " " + nameParts [1]

        holder.text.text = name.replace(" ", "\n")

        holder.itemView.setOnClickListener {

            if(currentItem.UID == UserData.instance.UID){
                MyApplication.openProfile.invoke(holder.itemView.context)
            }
            else{
                UserFragment.open(context, currentItem.UID)

            }

        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.image_pfp)
        val outline : MaterialCardView = itemView.findViewById(R.id.card_pfp_outline)
        val text : TextView = itemView.findViewById(R.id.text_name)
    }

}