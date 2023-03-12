package com.example.koller.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.*
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var eventsDataArrayList: ArrayList<EventsData>

    private lateinit var todayRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>

    private lateinit var viewStayOutSlider: View
    private lateinit var viewLessonSlider: View

    var time : Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var outgoingTimer : CountDownTimer
    lateinit var lessonTimer : CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        
        viewStayOutSlider = view.findViewById(R.id.home_view_stay_slider)
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

        outgoingTimer = object : CountDownTimer(1000000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                (viewStayOutSlider.layoutParams as ConstraintLayout.LayoutParams)
                    .matchConstraintPercentWidth = ((millisUntilFinished.toFloat() / 1000000) * -1) + 1
                viewStayOutSlider.requestLayout()
            }

            override fun onFinish() {
                (viewStayOutSlider.layoutParams as ConstraintLayout.LayoutParams)
                    .matchConstraintPercentWidth = 1f
                viewStayOutSlider.requestLayout()
            }
        }.start()

        lessonTimer = object : CountDownTimer(100000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                (viewLessonSlider.layoutParams as ConstraintLayout.LayoutParams)
                    .matchConstraintPercentWidth = ((millisUntilFinished.toFloat() / 100000) * -1) + 1
                viewLessonSlider.requestLayout()
            }

            override fun onFinish() {
                (viewLessonSlider.layoutParams as ConstraintLayout.LayoutParams)
                    .matchConstraintPercentWidth = 1f
                viewLessonSlider.requestLayout()
            }
        }.start()







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
        lessonTimer.cancel()
        outgoingTimer.cancel()
        super.onDestroyView()
    }
}