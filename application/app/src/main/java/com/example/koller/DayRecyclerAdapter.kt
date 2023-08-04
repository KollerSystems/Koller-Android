package com.example.koller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.example.shared.data.StarData
import com.example.shared.data.TodayData
import com.example.shared.recycleradapter.StarsRecyclerAdapter

class DayRecyclerAdapter  (private val dayList : ArrayList<TodayData>) : RecyclerView.Adapter<DayRecyclerAdapter.TodayViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(com.example.koller.R.layout.fragment_day, parent, false)
        return TodayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = dayList[position]

    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

    }

}