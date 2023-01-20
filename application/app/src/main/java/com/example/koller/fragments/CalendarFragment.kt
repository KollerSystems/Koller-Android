package com.example.koller.fragments

import android.R.id.tabs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.koller.R
import com.example.koller.TodayData
import com.example.koller.TodayRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList


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
    private lateinit var days : Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)

        days = arrayOf(getString(com.example.koller.R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday))

        val viewPager : ViewPager2 = view.findViewById(R.id.calendar_pager)
        viewPager.adapter = CalendarDayAdapter(this)

        val tabLayoutDays : TabLayout = view.findViewById(R.id.tab_layout_days)
        TabLayoutMediator(tabLayoutDays,
            viewPager,
        TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.text = days[position] }).attach()

        // turn off that tooltip text thing immediately on activity creation
        // turn off that tooltip text thing immediately on activity creation
        for (i in 0 until tabLayoutDays.tabCount) {
            Objects.requireNonNull(tabLayoutDays.getTabAt(i))
                ?.let { TooltipCompat.setTooltipText(it.view, null) }
        }

        tabLayoutDays.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // Repeat of the code above -- tooltips reset themselves after any tab relayout, so I
                // have to constantly keep turning them off again.
                for (i in 0 until tabLayoutDays.tabCount) {
                    Objects.requireNonNull(tabLayoutDays.getTabAt(i))?.let {
                        TooltipCompat.setTooltipText(
                            it.view,
                            null
                        )
                    }
                }
            }

            override fun onTabUnselected(tabLayoutDays: TabLayout.Tab) {}
            override fun onTabReselected(tabLayoutDays: TabLayout.Tab) {}
        })

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

    class CalendarDayAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)
    {
        override fun getItemCount(): Int {
            return 7
        }

        override fun createFragment(position: Int): Fragment {
            return DayFragment()
        }

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