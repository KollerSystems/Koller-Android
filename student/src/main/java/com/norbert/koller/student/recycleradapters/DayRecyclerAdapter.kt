package com.norbert.koller.student.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.recycleradapters.Comparator
import com.norbert.koller.student.R

class DayRecyclerAdapter : PagingDataAdapter<Any, RecyclerView.ViewHolder>(Comparator){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_day, parent, false)
        return TodayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as TodayViewHolder
    }

    override fun getItemCount(): Int {
        return 365
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}