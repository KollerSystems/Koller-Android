package com.example.koller.fragments

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.TodayData
import com.example.koller.TodayRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.ArrayList

class DayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>

    private lateinit var bottomSheetDuty: FrameLayout

    private lateinit var panelDutyMax: ViewGroup
    private lateinit var panelDutyMin: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_day, container, false)

        val ediaryButton: Button = view.findViewById(R.id.ediary_button)

        ediaryButton.setOnClickListener {

            val intent = Intent()
            intent.component = ComponentName("hu.filc.naplo", "hu.filc.naplo.MainActivity")
            intent.putExtra("tab", 3)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val packageManager = requireActivity().packageManager

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent);

            }
        }

        usersRecyclerView = view.findViewById(R.id.on_duty_recycler_view)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData("Nagy Gazsi", "F3", "🐔"),
            TodayData("Kis Péter", "F2", "🥶"),
            TodayData("Hatalmas Norbert", "L1", "🤓"),
            TodayData("Rogán Mátyás", "L3", "🦭")
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

        return view
    }

}