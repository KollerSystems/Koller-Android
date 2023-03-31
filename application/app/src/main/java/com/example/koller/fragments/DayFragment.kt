package com.example.koller.fragments

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.*
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>

    private lateinit var bottomSheetDuty: FrameLayout

    private lateinit var panelDutyMax: ViewGroup
    private lateinit var panelDutyMin: ViewGroup

    private lateinit var textSleepEnd : TextView
    private lateinit var textDaytimeOut : TextView
    private lateinit var textDaytimeIn : TextView
    private lateinit var textNighttimeOut : TextView
    private lateinit var textNighttimeIn : TextView
    private lateinit var textSleepStart : TextView
    private lateinit var lessonsRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_day, container, false)

        textSleepEnd = view.findViewById(R.id.calendar_day_text_sleep_end_time)
        textDaytimeOut = view.findViewById(R.id.calendar_day_text_daytime_out_time)
        textDaytimeIn = view.findViewById(R.id.calendar_day_text_daytime_in_time)
        textNighttimeOut = view.findViewById(R.id.calendar_day_text_nighttime_out_time)
        textNighttimeIn = view.findViewById(R.id.calendar_day_text_night_time_in_time)
        textSleepStart = view.findViewById(R.id.calendar_day_text_sleep_start_time)

        textSleepEnd.text = MyApplication.timeTo(DefaultDayTimes.instance.dayTimeStart)
        textDaytimeOut.text = MyApplication.timeFromTo(DefaultDayTimes.instance.dayTimeStart, DefaultDayTimes.instance.dayTimeGoInside)
        textDaytimeIn.text = MyApplication.timeFromTo(DefaultDayTimes.instance.dayTimeGoInside, DefaultDayTimes.instance.lessons[0].from)
        textNighttimeOut.text = MyApplication.timeFromTo(DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size-1].to, DefaultDayTimes.instance.nightTimeGoInsideYellow)
        textNighttimeIn.text = MyApplication.timeFromTo(DefaultDayTimes.instance.nightTimeGoInsideYellow, DefaultDayTimes.instance.nightTimeEnd)
        textSleepStart.text = MyApplication.timeFrom(DefaultDayTimes.instance.nightTimeEnd)



        val ediaryButton: Button = view.findViewById(R.id.ediary_button)

        ediaryButton.setOnClickListener{

        }


        usersRecyclerView = view.findViewById(R.id.on_duty_recycler_view)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData(context?.getDrawable(R.drawable.person),"Nagy Gazsi", "F3", ),
            TodayData(context?.getDrawable(R.drawable.person),"Kis Péter", "F2", ),
            TodayData(context?.getDrawable(R.drawable.person),"Hatalmas Norbert", "L1", ),
            TodayData(context?.getDrawable(R.drawable.person),"Rogán Mátyás", "L3", )
        )

        usersRecyclerView.adapter = TodayRecyclerAdapter(todayDataArrayList)

        bottomSheetDuty = view.findViewById(R.id.bottom_sheet_duty)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDuty);

        panelDutyMax = view.findViewById(R.id.panel_duty_full)
        panelDutyMin = view.findViewById(R.id.panel_duty_min)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {


                val drawerHeight = bottomSheet.height * slideOffset
                var textMyDutyName = view.findViewById<TextView>(R.id.text_close_duty_name)

                panelDutyMax.alpha = (slideOffset * 2) -1
                panelDutyMin.alpha = (((slideOffset -1) * 2) * -1) -1

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
        })

        var textCloseDutyName : TextView = view.findViewById(R.id.text_close_duty_name)
        textCloseDutyName.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.userFragment)
        }

        lessonsRecyclerView = view.findViewById(R.id.calendar_day_recycleview)
        lessonsRecyclerView.layoutManager = LinearLayoutManager(context)
        lessonsRecyclerView.setHasFixedSize(true)

        lessonsRecyclerView.adapter = LessonsRecyclerAdapter(DefaultDayTimes.instance.lessons)

        return view
    }

}

class LessonsRecyclerAdapter (private val lessonList : ArrayList<FromTo>) : RecyclerView.Adapter<LessonsRecyclerAdapter.LessonsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_lesson, parent, false)
        return LessonsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val currentItem = lessonList[position]
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
        val title : TextView = itemView.findViewById(R.id.lesson_text_title)
        val place : TextView = itemView.findViewById(R.id.lesson_text_place)
        val index : TextView = itemView.findViewById(R.id.lesson_text_index)
        val time : TextView = itemView.findViewById(R.id.lesson_text_time)
    }

}