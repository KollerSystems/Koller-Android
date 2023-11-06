package com.norbert.koller.shared.helpers

import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.norbert.koller.shared.MyApplication

class DateTimeHelper {

    companion object{

        val shortMonthDayTimeFormat = "MMM d. HH:mm"
        val yearShortMonthDayFormat = "yyyy. MMM d."
        val monthDay = "MMMM d."
        val shortMonthDayFormat = "MMM d."
        val timeFormat = "HH:mm"
        val apiFormat = "YYYY-MM-dd"

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

        fun setupDbd(textView : TextView) : MaterialDatePicker<Long> {

            val dpdb = MaterialDatePicker.Builder.datePicker()

            if(textView.text.isNullOrEmpty()){
                dpdb.setSelection(textView.tag as Long)
            }

            val dpd = dpdb.build()

            dpd.addOnPositiveButtonClickListener {selection ->


                textView.tag = selection
                textView.setText(java.text.SimpleDateFormat(shortMonthDayFormat).format(selection))


            }

            return dpd
        }
    }

}