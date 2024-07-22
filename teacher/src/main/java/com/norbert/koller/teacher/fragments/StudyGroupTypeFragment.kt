package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.teacher.pageradapter.ProgramTypeViewPagerAdapter
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.teacher.activities.EditSpecificStudyGroupActivity

class StudyGroupTypeFragment(val pid : Int? = null) : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_study_group_type, container, false)
        ViewCompat.setTransitionName(view!!.rootView, "cardTransition_${pid}position")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editButton : CardButton = view.findViewById(R.id.cb_edit)

        editButton.setOnClickListener {
            val intent = Intent(requireContext(), EditSpecificStudyGroupActivity::class.java)
            startActivity(intent)
        }

        val tabs : TabLayout = view.findViewById(Rs.id.tabs)

        val viewPager : ViewPager2 = view.findViewById(R.id.viewPager)

        val adapter = ProgramTypeViewPagerAdapter(pid, childFragmentManager, lifecycle)

        viewPager.adapter = adapter


        TabLayoutMediator(tabs, viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Adatok"
                }
                1->{
                    tab.text = "Résztvevők"
                }
                2->{
                    tab.text = "Elkönyvelt"
                }
            }
        }.attach()
    }

}