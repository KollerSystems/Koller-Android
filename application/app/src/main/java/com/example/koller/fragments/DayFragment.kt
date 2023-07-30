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

    companion object{
        var currentFragment : DayFragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: List<UserData>

    lateinit var bottomSheetDuty: FrameLayout

    private lateinit var panelDutyMax: ViewGroup
    private lateinit var panelDutyMin: ViewGroup
    private lateinit var panelDutyDragHandle: BottomSheetDragHandleView

    private lateinit var textSleepEnd : TextView
    private lateinit var textDaytimeOut : TextView
    private lateinit var textDaytimeIn : TextView
    private lateinit var textNighttimeOut : TextView
    private lateinit var textNighttimeIn : TextView
    private lateinit var textSleepStart : TextView
    private lateinit var lessonsRecyclerView : RecyclerView

    override fun onResume() {
        super.onResume()
        currentFragment = this

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(com.example.koller.R.layout.fragment_day, container, false)


        textSleepEnd = view.findViewById(com.example.koller.R.id.calendar_day_text_sleep_end_time)
        textDaytimeOut = view.findViewById(com.example.koller.R.id.calendar_day_text_daytime_out_time)
        textDaytimeIn = view.findViewById(com.example.koller.R.id.calendar_day_text_daytime_in_time)
        textNighttimeOut = view.findViewById(com.example.koller.R.id.calendar_day_text_nighttime_out_time)
        textNighttimeIn = view.findViewById(com.example.koller.R.id.calendar_day_text_night_time_in_time)
        textSleepStart = view.findViewById(com.example.koller.R.id.calendar_day_text_sleep_start_time)
        panelDutyDragHandle = view.findViewById(com.example.koller.R.id.drag_handle)

        textSleepEnd.text = MyApplication.timeTo(DefaultDayTimes.instance.dayTimeStart)
        textDaytimeOut.text = MyApplication.timeFromTo(DefaultDayTimes.instance.dayTimeStart, DefaultDayTimes.instance.dayTimeGoInside)
        textDaytimeIn.text = MyApplication.timeFromTo(DefaultDayTimes.instance.dayTimeGoInside, DefaultDayTimes.instance.lessons[0].from)
        textNighttimeOut.text = MyApplication.timeFromTo(DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size-1].to, DefaultDayTimes.instance.nightTimeGoInsideYellow)
        textNighttimeIn.text = MyApplication.timeFromTo(DefaultDayTimes.instance.nightTimeGoInsideYellow, DefaultDayTimes.instance.nightTimeEnd)
        textSleepStart.text = MyApplication.timeFrom(DefaultDayTimes.instance.nightTimeEnd)

        val textHeader : TextView = view.findViewById(R.id.calendar_text_header)



        //textHeader.text = MyApplication.simpleLocalMonthDay.format(myCalendar)




        val ediaryButton: Button = view.findViewById(R.id.ediary_button)

        ediaryButton.setOnClickListener{
            val packageName = "hu.ekreta.student"
            var intent: Intent? = requireContext().packageManager.getLaunchIntentForPackage(packageName)

            if (intent == null) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            }
            startActivity(intent)
        }


        usersRecyclerView = view.findViewById(com.example.koller.R.id.on_duty_recycler_view)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)

        todayDataArrayList = listOf(
            UserData(Name = "Nagy Gazsi"),
            UserData(Name = "Kis Péter"),
            UserData(Name = "Hatalmas Norbert"),
            UserData(Name = "Rogán Mátyás")
        )

        //usersRecyclerView.adapter = UserRecycleAdapter()

        bottomSheetDuty = view.findViewById(com.example.koller.R.id.bottom_sheet_duty)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDuty);

        bottomSheetDuty.post{
            panelDutyMax = view.findViewById(com.example.koller.R.id.panel_duty_full)
            panelDutyMin = view.findViewById(com.example.koller.R.id.panel_duty_min)


            val fullMinHeight = panelDutyMin.height + panelDutyDragHandle.height
            bottomSheetBehavior.peekHeight = fullMinHeight

            view.findViewById<LinearLayout>(com.example.koller.R.id.mcard_night).setPadding(0,0,0,fullMinHeight)
        }



        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                panelDutyMax.alpha = (slideOffset * 2) -1
                panelDutyMin.alpha = (((slideOffset -1) * 2) * -1) -1
                (parentFragment as CalendarDaysFragment).viewPager.isUserInputEnabled = false

                bottomSheetDuty.translationY = (slideOffset * -1 + 1)

            }



            override fun onStateChanged(bottomSheet: View, newState: Int) {
                (parentFragment as CalendarDaysFragment).viewPager.isUserInputEnabled = true
            }
        })

        var textCloseDutyName : TextView = view.findViewById(com.example.koller.R.id.text_close_duty_name)
        textCloseDutyName.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = com.example.koller.R.id.studentHostelNest
            Navigation.findNavController(view).navigate(com.example.koller.R.id.userFragment)
        }

        lessonsRecyclerView = view.findViewById(com.example.koller.R.id.calendar_day_recycleview)
        lessonsRecyclerView.layoutManager = LinearLayoutManager(context)
        lessonsRecyclerView.setHasFixedSize(true)

        lessonsRecyclerView.adapter = LessonsRecyclerAdapter(DefaultDayTimes.instance.lessons)

        return view
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