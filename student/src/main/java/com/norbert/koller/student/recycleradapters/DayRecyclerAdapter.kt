package com.norbert.koller.student.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.fragments.bottomsheet.DutyTeachersFragment
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.student.R

class DayRecyclerAdapter  (private val dayList : ArrayList<TodayData>) : RecyclerView.Adapter<DayRecyclerAdapter.TodayViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_calendar_days_day, parent, false)
        return TodayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        holder.mcardDutyTeachers.setOnClickListener{
            val dialog = DutyTeachersFragment()
            dialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, DutyTeachersFragment.TAG)
        }

    }

    override fun getItemCount(): Int {
        return 365
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val mcardDutyTeachers : MaterialCardView = itemView.findViewById(R.id.mcard_duty_teachers)

    }

}