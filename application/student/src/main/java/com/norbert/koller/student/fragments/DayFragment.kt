package com.norbert.koller.student.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.shared.data.DefaultDayTimes
import com.norbert.koller.shared.data.FromTo


class DayFragment : Fragment() {







}

class LessonsRecyclerAdapter (private val lessonList : ArrayList<FromTo>) : RecyclerView.Adapter<LessonsRecyclerAdapter.LessonsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(Rs.layout.view_lesson, parent, false)
        return LessonsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val currentItem = lessonList[position]
        MyApplication.roundRecyclerItemsVertically(holder.itemView, position, lessonList.size)
        holder.index.text = MyApplication.orderSingleNumber(holder.itemView.context, (position+1).toString())
        val currentLessonTime = DefaultDayTimes.instance.lessons[position]
        holder.time.text = MyApplication.timeFromTo(currentLessonTime.from, currentLessonTime.to)
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    class LessonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(Rs.id.lesson_text_title)
        val place : TextView = itemView.findViewById(Rs.id.lesson_text_place)
        val index : TextView = itemView.findViewById(Rs.id.lesson_text_index)
        val time : TextView = itemView.findViewById(Rs.id.lesson_text_time)
    }

}