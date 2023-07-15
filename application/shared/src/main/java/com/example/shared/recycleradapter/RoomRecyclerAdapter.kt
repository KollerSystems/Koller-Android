package com.example.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.example.shared.data.TodayData
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.imageview.ShapeableImageView


class RoomRecyclerAdapter (private var todayList : ArrayList<TodayData>, var context : Context) : RecyclerView.Adapter<RoomRecyclerAdapter.TodayViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_panel, parent, false)
        return TodayViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        holder.iconLeft.setImageDrawable(currentItem.iconLeft)
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description


        holder.itemView.setOnClickListener {

            (context as AppCompatActivity).intent.putExtra("roomID", currentItem.description)
            holder.itemView.findNavController().navigateWithDefaultAnimation(R.id.action_roomsFragment_to_roomFragment)

        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}