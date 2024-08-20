package com.norbert.koller.student.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout

import com.norbert.koller.student.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.student.activities.MainActivity
import com.norbert.koller.shared.data.DefaultDayTimes
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.util.*
import kotlin.math.roundToInt


class HomeFragment : com.norbert.koller.shared.fragments.HomeFragment() {

    lateinit var realViewStayOutSlider: View
    lateinit var realViewLessonSlider: View
    lateinit var textStayOutTop: TextView
    lateinit var textStayOutBottom: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun responsibleLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textStayOutTop = view.findViewById(R.id.home_text_outgoing_top)
        textStayOutBottom = view.findViewById(R.id.home_text_outgoing_down)
        realViewLessonSlider = view.findViewById(Rs.id.home_view_lesson_slider)
        realViewStayOutSlider = view.findViewById(com.norbert.koller.shared.R.id.home_view_stay_slider)




        val cardOutgoing : View = view.findViewById(R.id.home_card_outgoing)
        cardOutgoing.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView().selectedItemId = Rs.id.studentHostel
        }

        val cardLessons : View = view.findViewById(R.id.home_card_lessons)
        cardLessons.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView().selectedItemId = Rs.id.calendar
        }

        @SuppressLint("SetTextI18n")
        fun setupHome(){
            val c : Calendar = Calendar.getInstance()
            val seconds : Long = ((c.get(Calendar.SECOND) + c.get(Calendar.MINUTE) * 60 + c.get(
                Calendar.HOUR_OF_DAY) * 60 * 60) + (com.norbert.koller.shared.activities.SettingsActivity.timeOffset *60 *60)).toLong()
            val minutes : Float = seconds.toFloat() / 60

            fun startTimerForLessons(slider : View, startTimeMinute : Int, endTimeMinute : Int){
                val lastLessonMilli = (startTimeMinute * 60f * 1000).toLong()
                val nightTimeGoInMilli = (endTimeMinute * 60f * 1000).toLong()
                val remainingTimeOnUpdateMilli : Long = nightTimeGoInMilli - (seconds * 1000)

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

                val lastLessonMilli = (startTimeMinute * 60f * 1000).toLong()
                val nightTimeGoInMilli = (endTimeMinute * 60f * 1000).toLong()
                val remainingTimeOnUpdateMilli : Long = nightTimeGoInMilli - (seconds * 1000)

                val hours: Int = endTimeMinute / 60 // since both are ints, you get an int
                var minutes: String = (endTimeMinute % 60).toString()
                if(minutes.length == 1) minutes = "0$minutes"
                val nightTimeGoInsideForText = "$hours:$minutes"

                var outStringId = 0
                var remainStringId = 0
                if(false){ // inside
                    outStringId = com.norbert.koller.shared.R.string.go_out_text
                    remainStringId = com.norbert.koller.shared.R.string.will_remain
                }
                else{
                    outStringId = com.norbert.koller.shared.R.string.stay_out_text
                    remainStringId = com.norbert.koller.shared.R.string.now_remain
                }
                textStayOutTop.text = getString(outStringId, nightTimeGoInsideForText)

                outgoingTimer = object : CountDownTimer(remainingTimeOnUpdateMilli, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val solid : Float = (remainingTimeOnUpdateMilli.toFloat() /  (nightTimeGoInMilli - lastLessonMilli) * -1) + 1
                        val moving : Float = (((millisUntilFinished.toFloat() / remainingTimeOnUpdateMilli) * -1) + 1) * (1 - solid)
                        outgoingTimerRunning = true

                        if((millisUntilFinished / 1000f / 60 / 60) < 0.25f){
                            slider.setBackgroundColor(requireContext().getAttributeColor(com.google.android.material.R.attr.colorErrorContainer))
                        }
                        else{
                            slider.setBackgroundColor(requireContext().getAttributeColor( com.google.android.material.R.attr.colorPrimaryContainer))
                        }

                        var remainText : String = ""

                        val hoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60 + 1) / 60) - 0.5f).roundToInt()
                        val minutesWithoutHoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60) - (hoursUntilFinished * 60) + 0.5f)).roundToInt()

                        if(hoursUntilFinished != 0){
                            val hoursString : String
                            if(minutesWithoutHoursUntilFinished != 0){
                                hoursString = if(hoursUntilFinished > 1) {
                                    getString(com.norbert.koller.shared.R.string.hours)
                                } else{
                                    getString(com.norbert.koller.shared.R.string.hour)
                                }
                            }
                            else{
                                hoursString = if(hoursUntilFinished > 1) {
                                    getString(com.norbert.koller.shared.R.string.hours_infelcted)
                                } else{
                                    getString(com.norbert.koller.shared.R.string.hour_inflected)
                                }
                            }
                            remainText += "$hoursUntilFinished $hoursString "
                        }
                        if(minutesWithoutHoursUntilFinished != 0){

                            val minutesString : String
                            minutesString = if(minutesWithoutHoursUntilFinished > 1) {
                                getString(com.norbert.koller.shared.R.string.minutes_infelcted)
                            } else{
                                getString(com.norbert.koller.shared.R.string.minute)
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

            var lessonTime = false

            val textLessonsSilenceWarning : TextView = view.findViewById(R.id.home_text_silence_warning)

            val textLessonsTitle : TextView = view.findViewById(Rs.id.home_text_lessons_title)
            val textLessonsDescription : TextView = view.findViewById(Rs.id.home_text_lessons_description)
            val textLessonsNumber : TextView = view.findViewById(Rs.id.home_text_lessons_number)
            val textLessonsTime : TextView = view.findViewById(Rs.id.home_text_lessons_time)

            val realViewLessonsNext : View = view.findViewById(Rs.id.home_layout_lessons_next)
            var textSmallLessonsTitle : TextView = view.findViewById(Rs.id.home_text_small_lessons_title)
            var textSmallLessonsDescription : TextView = view.findViewById(Rs.id.home_text_small_lessons_description)
            val textSmallLessonsNumber : TextView = view.findViewById(Rs.id.home_text_small_lessons_number)
            val textSmallLessonsTime : TextView = view.findViewById(Rs.id.home_text_small_lessons_time)

            @SuppressLint("SetTextI18n")
            fun nextLessonsGraphic(index : Int){
                realViewLessonsNext.visibility = View.VISIBLE

                val lesson = DefaultDayTimes.instance.lessons[index + 1]
                textSmallLessonsNumber.text = (index + 1 + 1).toString()
                textSmallLessonsTime.text = DateTimeHelper.timeFromTo(lesson.from, lesson.to)
            }

            for (i in 0 until DefaultDayTimes.instance.lessons.size){
                //óra
                if(minutes >= DefaultDayTimes.instance.lessons[i].from &&
                    minutes <= DefaultDayTimes.instance.lessons[i].to){
                    lessonTime = true

                    val currentLessonTime = DefaultDayTimes.instance.lessons[i]

                    cardLessons.visibility = View.VISIBLE
                    textLessonsSilenceWarning.visibility = View.VISIBLE

                    textLessonsNumber.text = (i + 1).toString()
                    textLessonsNumber.background = null

                    textLessonsTime.text = DateTimeHelper.timeFromTo(currentLessonTime.from, currentLessonTime.to)

                    startTimerForLessons(realViewLessonSlider, currentLessonTime.from, currentLessonTime.to)

                    if(i < DefaultDayTimes.instance.lessons.size - 1){
                        var nextLessonTime = DefaultDayTimes.instance.lessons[i + 1]
                        nextLessonsGraphic(i)
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

                    nextLessonsGraphic(i)

                    val currentLessonTime = DefaultDayTimes.instance.lessons[i]
                    val nextLessonTime = DefaultDayTimes.instance.lessons[i + 1]

                    cardLessons.visibility = View.VISIBLE
                    textLessonsSilenceWarning.visibility = View.GONE


                    textLessonsTitle.text = getText(com.norbert.koller.shared.R.string.pause)
                    textLessonsDescription.text = getText(com.norbert.koller.shared.R.string.pause_description)
                    textLessonsNumber.text = ""
                    textLessonsNumber.background = AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.pause)
                    textLessonsTime.text = DateTimeHelper.timeFromTo(currentLessonTime.to, nextLessonTime.from)

                    startTimerForLessons(realViewLessonSlider, currentLessonTime.to, nextLessonTime.from)
                }
            }

            if(!lessonTime){
                cardLessons.visibility = View.GONE
            }


            val textNow : TextView = view.findViewById(com.norbert.koller.shared.R.id.home_text_now)

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

