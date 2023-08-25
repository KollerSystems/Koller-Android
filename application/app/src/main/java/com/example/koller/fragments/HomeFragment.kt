package com.example.koller.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.koller.R
import com.example.shared.R as Rs
import com.example.koller.activities.MainActivity
import com.example.shared.MyApplication
import com.example.shared.data.DefaultDayTimes
import java.util.*
import kotlin.math.roundToInt


class HomeFragment : com.example.shared.fragments.HomeFragment() {

    lateinit var realViewStayOutSlider: View
    lateinit var realViewLessonSlider: View
    lateinit var textStayOutTop: TextView
    lateinit var textStayOutBottom: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun ResponsibleLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textStayOutTop = view.findViewById(R.id.home_text_outgoing_top)
        textStayOutBottom = view.findViewById(R.id.home_text_outgoing_down)
        realViewLessonSlider = view.findViewById(Rs.id.home_view_lesson_slider)
        realViewStayOutSlider = view.findViewById(com.example.shared.R.id.home_view_stay_slider)




        val cardOutgoing : View = view.findViewById(R.id.home_card_outgoing)
        cardOutgoing.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = Rs.id.studentHostel
            findNavController().navigate(Rs.id.userOutgoingFragment)
        }

        val cardLessons : View = view.findViewById(R.id.home_card_lessons)
        cardLessons.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = Rs.id.calendar
        }

        fun setupHome(){
            var c : Calendar = Calendar.getInstance()
            val seconds : Long = ((c.get(Calendar.SECOND) + c.get(Calendar.MINUTE) * 60 + c.get(
                Calendar.HOUR_OF_DAY) * 60 * 60) + (com.example.shared.activities.SettingsActivity.timeOffset *60 *60)).toLong()
            val minutes : Float = seconds.toFloat() / 60

            fun startTimerForLessons(slider : View, startTimeMinute : Int, endTimeMinute : Int){
                var lastLessonMilli = (startTimeMinute * 60f * 1000).toLong()
                var nightTimeGoInMilli = (endTimeMinute * 60f * 1000).toLong()
                var remainingTimeOnUpdateMilli : Long = nightTimeGoInMilli - (seconds * 1000)

                outgoingTimer = object : CountDownTimer(remainingTimeOnUpdateMilli, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val solid : Float = (remainingTimeOnUpdateMilli.toFloat() /  (nightTimeGoInMilli - lastLessonMilli) * -1) + 1
                        val moving : Float = (((millisUntilFinished.toFloat() / remainingTimeOnUpdateMilli) * -1) + 1) * (1 - solid)

                        (slider.layoutParams as ConstraintLayout.LayoutParams)
                            .matchConstraintPercentWidth = solid + moving
                        slider.requestLayout()
                    }

                    override fun onFinish() {
                        lessonsTimerRunning = false
                    }
                }.start()
            }

            fun startTimerForOutgoing (slider : View, startTimeMinute : Int, endTimeMinute : Int){

                var lastLessonMilli = (startTimeMinute * 60f * 1000).toLong()
                var nightTimeGoInMilli = (endTimeMinute * 60f * 1000).toLong()
                var remainingTimeOnUpdateMilli : Long = nightTimeGoInMilli - (seconds * 1000)

                val hours: Int = endTimeMinute / 60 // since both are ints, you get an int
                var minutes: String = (endTimeMinute % 60).toString()
                if(minutes.length == 1) minutes = "0$minutes"
                val nightTimeGoInsideForText : String = "$hours:$minutes"

                var outStringId : Int = 0
                var remainStringId : Int = 0
                if(false){ // inside
                    outStringId = com.example.shared.R.string.go_out_text
                    remainStringId = com.example.shared.R.string.will_remain
                }
                else{
                    outStringId = com.example.shared.R.string.stay_out_text
                    remainStringId = com.example.shared.R.string.now_remain
                }
                textStayOutTop.text = getString(outStringId, nightTimeGoInsideForText)

                outgoingTimer = object : CountDownTimer(remainingTimeOnUpdateMilli, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val solid : Float = (remainingTimeOnUpdateMilli.toFloat() /  (nightTimeGoInMilli - lastLessonMilli) * -1) + 1
                        val moving : Float = (((millisUntilFinished.toFloat() / remainingTimeOnUpdateMilli) * -1) + 1) * (1 - solid)
                        outgoingTimerRunning = true

                        if((millisUntilFinished / 1000f / 60 / 60) < 0.25f){
                            slider.setBackgroundColor(MyApplication.getAttributeColor(requireContext(), com.google.android.material.R.attr.colorErrorContainer))
                        }
                        else{
                            slider.setBackgroundColor(MyApplication.getAttributeColor(requireContext(), com.google.android.material.R.attr.colorPrimaryContainer))
                        }

                        var remainText : String = ""

                        var hoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60 + 1) / 60) - 0.5f).roundToInt()
                        var minutesWithoutHoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60) - (hoursUntilFinished * 60) + 0.5f)).roundToInt()

                        if(hoursUntilFinished != 0){
                            var hoursString : String
                            if(minutesWithoutHoursUntilFinished != 0){
                                if(hoursUntilFinished > 1) {
                                    hoursString = getString(com.example.shared.R.string.hours)
                                }
                                else{
                                    hoursString = getString(com.example.shared.R.string.hour)
                                }
                            }
                            else{
                                if(hoursUntilFinished > 1) {
                                    hoursString = getString(com.example.shared.R.string.hours_infelcted)
                                }
                                else{
                                    hoursString = getString(com.example.shared.R.string.hour_inflected)
                                }
                            }
                            remainText += "$hoursUntilFinished $hoursString "
                        }
                        if(minutesWithoutHoursUntilFinished != 0){

                            var minutesString : String
                            if(minutesWithoutHoursUntilFinished > 1) {
                                minutesString = getString(com.example.shared.R.string.minutes_infelcted)
                            }
                            else{
                                minutesString = getString(com.example.shared.R.string.minute)
                            }

                            remainText += "$minutesWithoutHoursUntilFinished $minutesString "
                        }


                        textStayOutBottom.text = getString(remainStringId, remainText)

                        (slider.layoutParams as ConstraintLayout.LayoutParams)
                            .matchConstraintPercentWidth = solid + moving
                        slider.requestLayout()
                    }

                    override fun onFinish() {
                        outgoingTimerRunning = false
                    }
                }.start()
            }

            if(minutes >= DefaultDayTimes.instance.dayTimeStart &&
                minutes <= DefaultDayTimes.instance.dayTimeGoInside){

                cardOutgoing.visibility = View.VISIBLE

                startTimerForOutgoing(realViewStayOutSlider, DefaultDayTimes.instance.dayTimeStart, DefaultDayTimes.instance.dayTimeGoInside)
            }
            else if(minutes >= DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size -1].to &&
                minutes <= DefaultDayTimes.instance.nightTimeGoInsideYellow){

                cardOutgoing.visibility = View.VISIBLE

                startTimerForOutgoing(realViewStayOutSlider, DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size -1].to, DefaultDayTimes.instance.nightTimeGoInsideYellow)

            }
            else{
                cardOutgoing.visibility = View.GONE
            }

            var lessonTime : Boolean = false

            var textLessonsSilenceWarning : TextView = view.findViewById(R.id.home_text_silence_warning)

            var textLessonsTitle : TextView = view.findViewById(Rs.id.home_text_lessons_title)
            var textLessonsDescription : TextView = view.findViewById(Rs.id.home_text_lessons_description)
            var textLessonsNumber : TextView = view.findViewById(Rs.id.home_text_lessons_number)
            var textLessonsTime : TextView = view.findViewById(Rs.id.home_text_lessons_time)

            var realViewLessonsNext : View = view.findViewById(Rs.id.home_layout_lessons_next)
            var textSmallLessonsTitle : TextView = view.findViewById(Rs.id.home_text_small_lessons_title)
            var textSmallLessonsDescription : TextView = view.findViewById(Rs.id.home_text_small_lessons_description)
            var textSmallLessonsNumber : TextView = view.findViewById(Rs.id.home_text_small_lessons_number)
            var textSmallLessonsTime : TextView = view.findViewById(Rs.id.home_text_small_lessons_time)

            fun NextLessonsGraphic(index : Int){
                realViewLessonsNext.visibility = View.VISIBLE

                var lesson = DefaultDayTimes.instance.lessons[index + 1]
                textSmallLessonsNumber.text = (index + 1 + 1).toString() + "."
                textSmallLessonsTime.text = MyApplication.timeFromTo(lesson.from, lesson.to)
            }

            for (i in 0 until DefaultDayTimes.instance.lessons.size){
                //óra
                if(minutes >= DefaultDayTimes.instance.lessons[i].from &&
                    minutes <= DefaultDayTimes.instance.lessons[i].to){
                    lessonTime = true

                    var currentLessonTime = DefaultDayTimes.instance.lessons[i]

                    cardLessons.visibility = View.VISIBLE
                    textLessonsSilenceWarning.visibility = View.VISIBLE

                    textLessonsNumber.text = (i + 1).toString() + "."
                    textLessonsNumber.background = null

                    textLessonsTime.text = MyApplication.timeFromTo(currentLessonTime.from, currentLessonTime.to)

                    startTimerForLessons(realViewLessonSlider, currentLessonTime.from, currentLessonTime.to)

                    if(i < DefaultDayTimes.instance.lessons.size - 1){
                        var nextLessonTime = DefaultDayTimes.instance.lessons[i + 1]
                        NextLessonsGraphic(i)
                    }
                    else{
                        realViewLessonsNext.visibility = View.GONE
                    }

                }
                //szünet
                else if (i < DefaultDayTimes.instance.lessons.size - 1 &&
                    minutes >= DefaultDayTimes.instance.lessons[i].to &&
                    minutes <= DefaultDayTimes.instance.lessons[i + 1].from){
                    lessonTime = true

                    NextLessonsGraphic(i)

                    var currentLessonTime = DefaultDayTimes.instance.lessons[i]
                    var nextLessonTime = DefaultDayTimes.instance.lessons[i + 1]

                    cardLessons.visibility = View.VISIBLE
                    textLessonsSilenceWarning.visibility = View.GONE


                    textLessonsTitle.text = getText(com.example.shared.R.string.pause)
                    textLessonsDescription.text = getText(com.example.shared.R.string.pause_description)
                    textLessonsNumber.text = ""
                    textLessonsNumber.background = requireContext().getDrawable(com.example.shared.R.drawable.pause)
                    textLessonsTime.text = MyApplication.timeFromTo(currentLessonTime.to, nextLessonTime.from)

                    startTimerForLessons(realViewLessonSlider, currentLessonTime.to, nextLessonTime.from)
                }
            }

            if(!lessonTime){
                cardLessons.visibility = View.GONE
            }


            val textNow : TextView = view.findViewById(com.example.shared.R.id.home_text_now)

            if(cardOutgoing.visibility == View.VISIBLE || cardLessons.visibility == View.VISIBLE){
                textNow.visibility = View.VISIBLE
            }
            else{
                textNow.visibility = View.GONE
            }
        }

        setupHome()

        refresh.setOnRefreshListener{
            cancelIfNeeded()
            setupHome()
            refresh.isRefreshing = false
        }
    }

    fun cancelIfNeeded(){
        if (lessonsTimerRunning)
            lessonTimer.cancel()
        if (outgoingTimerRunning)
            outgoingTimer.cancel()
    }

    override fun onDestroyView() {
        cancelIfNeeded()
        super.onDestroyView()
    }

}

