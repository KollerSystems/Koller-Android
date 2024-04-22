package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.shared.fragments.UserOutgoingViewPagerAdapter
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.teacher.ProgramTypeViewPagerAdapter
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.teacher.activities.EditSpecificStudyGroupActivity
import com.skydoves.androidveil.VeilLayout
import retrofit2.Response

class StudyGroupTypeFragment(val pid : Int? = null) : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_study_group_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editButton : SimpleCardButton = view.findViewById(R.id.Scb_edit)

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