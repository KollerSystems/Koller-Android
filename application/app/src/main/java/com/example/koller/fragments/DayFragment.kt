package com.example.koller.fragments

import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.activities.MainActivity
import com.example.shared.MyApplication
import com.example.koller.R
import com.example.shared.R as Rs
import com.example.shared.data.DefaultDayTimes
import com.example.shared.data.FromTo
import com.example.shared.data.UserData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDragHandleView


class DayFragment() : Fragment() {


    override fun onResume() {
        super.onResume()

    }




}

class LessonsRecyclerAdapter (private val lessonList : ArrayList<FromTo>) : RecyclerView.Adapter<LessonsRecyclerAdapter.LessonsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(Rs.layout.view_lesson, parent, false)
        return LessonsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val currentItem = lessonList[position]
        MyApplication.roundRecyclerItemsVertically(holder.itemView.context, holder.itemView, position, lessonList.size)
        holder.title.text = "Title"
        holder.place.text = "Place"
        holder.index.text = (position+1).toString()+"."
        var currentLessonTime = DefaultDayTimes.instance.lessons[position]
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