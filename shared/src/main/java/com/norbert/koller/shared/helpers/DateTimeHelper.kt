package com.norbert.koller.shared.helpers

import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.norbert.koller.shared.managers.formatDate

class DateTimeHelper {

    companion object{

        const val TIME_IMPORTANT = 5
        const val TIME_LESS_IMPORTANT = 1*60*60*24
        const val TIME_NOT_IMPORTANT = 7*60*60*24
        const val TIME_NOT_IMPORTANT_AT_ALL = 4*7*60*60*24

        const val monthDayWeekdayFormat = "MMMM d. EEEE"
        const val shortMonthDayTimeFormat = "MMM d. HH:mm"
        const val yearShortMonthDayFormat = "yyyy. MMM d."
        const val monthDay = "MMMM d."
        const val shortMonthDayFormat = "MMM d."
        const val timeFormat = "HH:mm"
        const val apiFormat = "YYYY-MM-dd"

        fun timeTo(to : Int):String{
            return "-\n" + minuteToHourMinuteFormat(to)
        }

        fun timeFromTo(from : Int, to : Int):String{
            return minuteToHourMinuteFormat(from) + "\n-\n" + minuteToHourMinuteFormat(to)
        }

        fun timeFrom(from : Int):String{
            return minuteToHourMinuteFormat(from) + "\n-"
        }

        fun minuteToHourMinuteFormat(minute : Int):String{


            val hours : Int = ((minute) / 60)
            val minutesWithoutHours : Int = (minute - (hours * 60))

            return (hours.toString()+":"+minutesWithoutHours.toString().padStart(2, '0'))
        }

        fun timeToString(hours : Int, minutes : Int) : String{
            return (hours).toString().padStart(2, '0')+":"+(minutes).toString().padStart(2, '0')
        }

        fun timeFromString(time : String?) : Pair<Int, Int>{

            if(time.isNullOrEmpty()) return Pair(12,0)

            val values : List<String> = time.split(":")
            val hours : Int = values[0].toInt()
            val minutes : Int = values[1].toInt()

            return Pair(hours, minutes)
        }

        fun setupDbd(textView : TextView, style : String = shortMonthDayFormat) : MaterialDatePicker<Long> {

            val dpdb = MaterialDatePicker.Builder.datePicker()

            if(textView.tag is Long){
                dpdb.setSelection(textView.tag as Long)
            }

            val dpd = dpdb.build()

            dpd.addOnPositiveButtonClickListener {selection ->

                textView.tag = selection
                textView.text = selection.formatDate(style)
            }

            return dpd
        }

        fun setupTimePickerDialog(textView : TextView) : MaterialTimePicker {

            val timePickerDialogBuilder = MaterialTimePicker.Builder()

            if(textView.tag is Pair<*, *>){
                val pair : Pair<Int, Int> = textView.tag as Pair<Int, Int>
                timePickerDialogBuilder.setHour(pair.first)
                timePickerDialogBuilder.setMinute(pair.second)
            }

            val timePickerDialog = timePickerDialogBuilder.build()

            timePickerDialog.addOnPositiveButtonClickListener {

                textView.tag = Pair(timePickerDialog.hour,timePickerDialog.minute)
                textView.text = timeToString(timePickerDialog.hour, timePickerDialog.minute)
            }

            return timePickerDialog
        }
    }

}