package com.example.koller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView


class TodayRecyclerAdapter (private val todayList : ArrayList<TodayData>) : RecyclerView.Adapter<TodayRecyclerAdapter.TodayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_panel, parent, false)
        return TodayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.icon.background = currentItem.icon
        holder.icon.text = currentItem.grade.toString()
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.comment_user_name)
        val description : TextView = itemView.findViewById(R.id.comment_text)
        val icon : TextView = itemView.findViewById(R.id.notification_icon)

        init {
            itemView.setOnClickListener {

                val dialog = RoomOrderBottomSheet()
                dialog.show((itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBottomSheet.TAG)
            }
        }
    }

}