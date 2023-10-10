package com.norbert.koller.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.DutyTeachersBshdFragment
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment

class DayRecyclerAdapter  (private val dayList : ArrayList<TodayData>) : RecyclerView.Adapter<DayRecyclerAdapter.TodayViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_day, parent, false)
        return TodayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = dayList[position]
        holder.mcardDutyTeachers.setOnClickListener{
            val dialog = DutyTeachersBshdFragment()
            dialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, DutyTeachersBshdFragment.TAG)
        }

    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val mcardDutyTeachers : MaterialCardView = itemView.findViewById(R.id.mcard_duty_teachers)

    }

}