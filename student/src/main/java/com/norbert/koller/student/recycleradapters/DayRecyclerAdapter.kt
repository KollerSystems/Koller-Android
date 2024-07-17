package com.norbert.koller.student.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapters.BaseComparator
import com.norbert.koller.student.R

class DayRecyclerAdapter : PagingDataAdapter<Any, RecyclerView.ViewHolder>(BaseComparator){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_calendar_days_day, parent, false)
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