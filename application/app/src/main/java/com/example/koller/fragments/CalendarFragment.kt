package com.example.koller.fragments

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.TodayData
import com.example.koller.TodayRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.w3c.dom.Text


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)

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
                var textMyDutyName = view.findViewById<TextView>(R.id.text_my_duty_name)

                panelDutyMax.alpha = slideOffset
                panelDutyMin.alpha = (slideOffset * -1) + 1

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
        })

        return view;
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}