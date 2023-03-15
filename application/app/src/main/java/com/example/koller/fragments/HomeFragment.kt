package com.example.koller.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.*
import java.util.*
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var eventsDataArrayList: ArrayList<EventsData>

    private lateinit var todayRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>

    private lateinit var viewStayOutSlider: View
    private lateinit var viewLessonSlider: View

    private lateinit var textStayOutTop: TextView
    private lateinit var textStayOutBottom: TextView

    var time : Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var outgoingTimer : CountDownTimer
    var outgoingTimerRunning = false
    lateinit var lessonTimer : CountDownTimer
    var lessonsTimerRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)

        viewStayOutSlider = view.findViewById(R.id.home_view_stay_slider)
        textStayOutTop = view.findViewById(R.id.home_text_outgoing_top)
        textStayOutBottom = view.findViewById(R.id.home_text_outgoing_down)
        viewLessonSlider = view.findViewById(R.id.home_view_lesson_slider)
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView)
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.setHasFixedSize(true)

        eventsDataArrayList = arrayListOf(
            EventsData("Titkos Burger", "A kód: Titkós\nLorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Hitler mégesm halt meg!", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Az összes magyar iskola felrobbant!", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Joe", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Juhos Gergely xdddd", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"))

        eventsRecyclerView.adapter = EventsRecyclerAdapter(eventsDataArrayList)

        val cardOutgoing : View = view.findViewById(R.id.home_card_outgoing)

        val cardLessons : View = view.findViewById(R.id.home_card_lessons)

        var c : Calendar = Calendar.getInstance()
        val seconds : Long = (c.get(Calendar.SECOND) + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.HOUR_OF_DAY) * 60 * 60).toLong()
        val minutes : Float = seconds.toFloat() / 60

        if(minutes > DefaultDayTimes.instance.dayTimeStart &&
            minutes < DefaultDayTimes.instance.dayTimeGoInside){

            cardOutgoing.visibility = VISIBLE

        }

        else if(minutes > DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size -1].to &&
            minutes < DefaultDayTimes.instance.nightTimeGoInsideYellow){

            cardOutgoing.visibility = VISIBLE

            var lastLessonMilli = (DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size -1].to * 60f * 1000).toLong()
            var nightTimeGoInMilli = (DefaultDayTimes.instance.nightTimeGoInsideYellow * 60f * 1000).toLong()
            var remainingTimeOnUpdateMilli : Long = nightTimeGoInMilli - (seconds * 1000)


            val nightTimeGoInsideForText : String = (DefaultDayTimes.instance.nightTimeGoInsideYellow / 60).toString()
            var outStringId : Int = 0
            var remainStringId : Int = 0
            if(false){ // inside
                outStringId = R.string.go_out_text
                remainStringId = R.string.will_remain
            }
            else{
                outStringId = R.string.stay_out_text
                remainStringId = R.string.now_remain
            }
            textStayOutTop.text = getString(outStringId, nightTimeGoInsideForText)

            outgoingTimer = object : CountDownTimer(remainingTimeOnUpdateMilli, 1) {
                override fun onTick(millisUntilFinished: Long) {
                    val solid : Float = (remainingTimeOnUpdateMilli.toFloat() /  (nightTimeGoInMilli - lastLessonMilli) * -1) + 1
                    val moving : Float = (((millisUntilFinished.toFloat() / remainingTimeOnUpdateMilli) * -1) + 1) * (1 - solid)
                    outgoingTimerRunning = true

                    if((millisUntilFinished / 1000f / 60 / 60) < 0.25f){
                        viewStayOutSlider.setBackgroundColor(MyApplication.getAttributeColor(requireContext(), com.google.android.material.R.attr.colorErrorContainer))
                    }
                    else{
                        viewStayOutSlider.setBackgroundColor(MyApplication.getAttributeColor(requireContext(), com.google.android.material.R.attr.colorPrimaryContainer))
                    }

                    var remainText : String = ""

                    var hoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60 + 1) / 60) - 0.5f).roundToInt()
                    var minutesWithoutHoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60) - (hoursUntilFinished * 60) + 0.5f)).roundToInt()

                    if(hoursUntilFinished != 0){
                        if(minutesWithoutHoursUntilFinished != 0){
                            remainText += hoursUntilFinished.toString() + " " + getString(R.string.hour) + " "
                        }
                        else{
                            remainText += hoursUntilFinished.toString() + " " + getString(R.string.hour_inflected) + " "
                        }
                    }
                    if(minutesWithoutHoursUntilFinished != 0){
                        remainText += minutesWithoutHoursUntilFinished.toString() +  " " + getString(R.string.minute) + " "
                    }


                    textStayOutBottom.text = getString(remainStringId, remainText)

                    (viewStayOutSlider.layoutParams as ConstraintLayout.LayoutParams)
                        .matchConstraintPercentWidth = solid + moving
                    viewStayOutSlider.requestLayout()
                }

                override fun onFinish() {
                    outgoingTimerRunning = false
                }
            }.start()

        }
        else{
            cardOutgoing.visibility = GONE
        }

        val textNow : TextView = view.findViewById(R.id.home_text_now)

        if(cardOutgoing.visibility == VISIBLE || cardLessons.visibility == VISIBLE){
            textNow.visibility = VISIBLE
        }
        else{
            textNow.visibility = GONE
        }

        todayRecyclerView = view.findViewById(R.id.todayRecyclerView)
        todayRecyclerView.layoutManager = LinearLayoutManager(context)
        todayRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData("Szobarend", "K, P", "4"),
            TodayData("Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mivel találkoztam vele a Mondoconon", context?.getDrawable(R.drawable.award)
            ))

        todayRecyclerView.adapter = TodayRecyclerAdapter(todayDataArrayList)

        val fabRoot: View = view.findViewById(R.id.home_fab_root)

        return view
    }

    override fun onDestroyView() {
        if (lessonsTimerRunning)
            lessonTimer.cancel()
        if (outgoingTimerRunning)
            outgoingTimer.cancel()
        super.onDestroyView()
    }
}