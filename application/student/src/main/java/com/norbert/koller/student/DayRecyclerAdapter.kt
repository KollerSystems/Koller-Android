package com.norbert.koller.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.data.TodayData

class DayRecyclerAdapter  (private val dayList : ArrayList<TodayData>) : RecyclerView.Adapter<DayRecyclerAdapter.TodayViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_day, parent, false)
        return TodayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = dayList[position]

    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}